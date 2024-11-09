package run.ikaros.server.cache;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import run.ikaros.api.cache.CacheEvict;
import run.ikaros.api.cache.CachePut;
import run.ikaros.api.cache.Cacheable;
import run.ikaros.server.infra.utils.JsonUtils;

@Aspect
@Component
public class CacheAspect {

    @Pointcut("@annotation(run.ikaros.api.cache.Cacheable)")
    public void cacheableMethods() {
    }

    @Pointcut("@annotation(run.ikaros.api.cache.CacheEvict)")
    public void cacheEvictMethods() {
    }

    @Pointcut("@annotation(run.ikaros.api.cache.CachePut)")
    public void cachePutMethods() {
    }


    private final ExpressionParser spelExpressionParser = new SpelExpressionParser();

    private final ReactiveCacheManager reactiveCacheManager;

    public CacheAspect(ReactiveCacheManager reactiveCacheManager) {
        this.reactiveCacheManager = reactiveCacheManager;
    }


    /**
     * 处理可缓存注解切面.
     */
    @Around("cacheableMethods() && @annotation(cacheable)")
    public Mono<Void> aroundCacheableMethods(ProceedingJoinPoint joinPoint, Cacheable cacheable)
        throws Throwable {
        final String cacheKeyPostfix = parseSpelExpression(cacheable.key(), joinPoint);
        return Flux.fromStream(Arrays.stream(cacheable.value()))
            .map(v -> v + cacheKeyPostfix)
            .flatMap(cacheKey -> {
                try {
                    return reactiveCacheManager.get(cacheKey)
                        .switchIfEmpty(reactiveCacheManager.put(cacheKey,
                                JsonUtils.obj2Json(joinPoint.proceed()))
                            .then(Mono.fromCallable(() -> cacheKey)));
                } catch (Throwable e) {
                    return Mono.error(new RuntimeException(e));
                }
            })
            .collectList()
            .then();
    }

    private String parseSpelExpression(String expression, ProceedingJoinPoint joinPoint) {
        final EvaluationContext context = new StandardEvaluationContext();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], paramValues[i]);
        }
        return spelExpressionParser.parseExpression(expression).getValue(context, String.class);
    }

    /**
     * 处理缓存移除注解切面.
     */
    @Around("cacheEvictMethods() && @annotation(cacheEvict)")
    public Mono<Void> aroundCacheEvictMethods(ProceedingJoinPoint joinPoint, CacheEvict cacheEvict)
        throws Throwable {
        final String cacheKeyPostfix = parseSpelExpression(cacheEvict.key(), joinPoint);
        return Flux.fromStream(Arrays.stream(cacheEvict.value()))
            .map(v -> v + cacheKeyPostfix)
            .flatMap(reactiveCacheManager::remove)
            .collectList()
            .then(Mono.just(joinPoint.proceed()))
            .then();
    }

    /**
     * 处理缓存更新注解切面.
     */
    @Around("cachePutMethods() && @annotation(cachePut)")
    public Mono<Void> aroundCachePutMethods(ProceedingJoinPoint joinPoint, CachePut cachePut)
        throws Throwable {
        final String result = JsonUtils.obj2Json(joinPoint.proceed());
        final String cacheKeyPostfix = parseSpelExpression(cachePut.key(), joinPoint);
        return Flux.fromStream(Arrays.stream(cachePut.value()))
            .map(v -> v + cacheKeyPostfix)
            .flatMap(cacheKey -> reactiveCacheManager.put(cacheKey, result))
            .collectList()
            .then();
    }
}
