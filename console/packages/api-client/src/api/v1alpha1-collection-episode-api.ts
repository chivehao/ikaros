/* tslint:disable */
/* eslint-disable */
/**
 * Ikaros Open API Documentation
 * Documentation for Ikaros Open API
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import type { Configuration } from "../configuration";
import type { AxiosPromise, AxiosInstance, AxiosRequestConfig } from "axios";
import globalAxios from "axios";
// Some imports not used depending on template conditions
// @ts-ignore
import {
  DUMMY_BASE_URL,
  assertParamExists,
  setApiKeyToObject,
  setBasicAuthToObject,
  setBearerAuthToObject,
  setOAuthToObject,
  setSearchParams,
  serializeDataIfNeeded,
  toPathString,
  createRequestFunction,
} from "../common";
// @ts-ignore
import {
  BASE_PATH,
  COLLECTION_FORMATS,
  RequestArgs,
  BaseAPI,
  RequiredError,
} from "../base";
// @ts-ignore
import { EpisodeCollection } from "../models";
/**
 * V1alpha1CollectionEpisodeApi - axios parameter creator
 * @export
 */
export const V1alpha1CollectionEpisodeApiAxiosParamCreator = function (
  configuration?: Configuration
) {
  return {
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteCollectionEpisode: async (
      episodeId: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'episodeId' is not null or undefined
      assertParamExists("deleteCollectionEpisode", "episodeId", episodeId);
      const localVarPath =
        `/api/v1alpha1/collection/episode/{episodeId}`.replace(
          `{${"episodeId"}}`,
          encodeURIComponent(String(episodeId))
        );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "DELETE",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    findCollectionEpisode: async (
      episodeId: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'episodeId' is not null or undefined
      assertParamExists("findCollectionEpisode", "episodeId", episodeId);
      const localVarPath =
        `/api/v1alpha1/collection/episode/{episodeId}`.replace(
          `{${"episodeId"}}`,
          encodeURIComponent(String(episodeId))
        );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "GET",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     *
     * @param {number} subjectId Subject id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    findCollectionEpisodesByUserIdAndSubjectId: async (
      subjectId: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'subjectId' is not null or undefined
      assertParamExists(
        "findCollectionEpisodesByUserIdAndSubjectId",
        "subjectId",
        subjectId
      );
      const localVarPath =
        `/api/v1alpha1/collection/episodes/subjectId/{subjectId}`.replace(
          `{${"subjectId"}}`,
          encodeURIComponent(String(subjectId))
        );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "GET",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    saveCollectionEpisode: async (
      episodeId: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'episodeId' is not null or undefined
      assertParamExists("saveCollectionEpisode", "episodeId", episodeId);
      const localVarPath =
        `/api/v1alpha1/collection/episode/{episodeId}`.replace(
          `{${"episodeId"}}`,
          encodeURIComponent(String(episodeId))
        );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "POST",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {number} progress Episode collection progress, unit is milliseconds.
     * @param {number} [duration] Episode collection duration, unit is milliseconds.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    updateCollectionEpisode: async (
      episodeId: number,
      progress: number,
      duration?: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'episodeId' is not null or undefined
      assertParamExists("updateCollectionEpisode", "episodeId", episodeId);
      // verify required parameter 'progress' is not null or undefined
      assertParamExists("updateCollectionEpisode", "progress", progress);
      const localVarPath =
        `/api/v1alpha1/collection/episode/{episodeId}`.replace(
          `{${"episodeId"}}`,
          encodeURIComponent(String(episodeId))
        );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "PUT",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      if (progress !== undefined) {
        localVarQueryParameter["progress"] = progress;
      }

      if (duration !== undefined) {
        localVarQueryParameter["duration"] = duration;
      }

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {boolean} finish Episode collection finish.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    updateCollectionEpisodeFinish: async (
      episodeId: number,
      finish: boolean,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'episodeId' is not null or undefined
      assertParamExists(
        "updateCollectionEpisodeFinish",
        "episodeId",
        episodeId
      );
      // verify required parameter 'finish' is not null or undefined
      assertParamExists("updateCollectionEpisodeFinish", "finish", finish);
      const localVarPath =
        `/api/v1alpha1/collection/episode/finish/{episodeId}/{finish}`
          .replace(`{${"episodeId"}}`, encodeURIComponent(String(episodeId)))
          .replace(`{${"finish"}}`, encodeURIComponent(String(finish)));
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = {
        method: "PUT",
        ...baseOptions,
        ...options,
      };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      // authentication BasicAuth required
      // http basic authentication required
      setBasicAuthToObject(localVarRequestOptions, configuration);

      // authentication BearerAuth required
      // http bearer authentication required
      await setBearerAuthToObject(localVarHeaderParameter, configuration);

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions =
        baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
  };
};

/**
 * V1alpha1CollectionEpisodeApi - functional programming interface
 * @export
 */
export const V1alpha1CollectionEpisodeApiFp = function (
  configuration?: Configuration
) {
  const localVarAxiosParamCreator =
    V1alpha1CollectionEpisodeApiAxiosParamCreator(configuration);
  return {
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteCollectionEpisode(
      episodeId: number,
      options?: AxiosRequestConfig
    ): Promise<
      (
        axios?: AxiosInstance,
        basePath?: string
      ) => AxiosPromise<EpisodeCollection>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.deleteCollectionEpisode(
          episodeId,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async findCollectionEpisode(
      episodeId: number,
      options?: AxiosRequestConfig
    ): Promise<
      (
        axios?: AxiosInstance,
        basePath?: string
      ) => AxiosPromise<EpisodeCollection>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.findCollectionEpisode(
          episodeId,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
    /**
     *
     * @param {number} subjectId Subject id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async findCollectionEpisodesByUserIdAndSubjectId(
      subjectId: number,
      options?: AxiosRequestConfig
    ): Promise<
      (
        axios?: AxiosInstance,
        basePath?: string
      ) => AxiosPromise<Array<EpisodeCollection>>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.findCollectionEpisodesByUserIdAndSubjectId(
          subjectId,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async saveCollectionEpisode(
      episodeId: number,
      options?: AxiosRequestConfig
    ): Promise<
      (
        axios?: AxiosInstance,
        basePath?: string
      ) => AxiosPromise<EpisodeCollection>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.saveCollectionEpisode(
          episodeId,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {number} progress Episode collection progress, unit is milliseconds.
     * @param {number} [duration] Episode collection duration, unit is milliseconds.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async updateCollectionEpisode(
      episodeId: number,
      progress: number,
      duration?: number,
      options?: AxiosRequestConfig
    ): Promise<
      (axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.updateCollectionEpisode(
          episodeId,
          progress,
          duration,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
    /**
     *
     * @param {number} episodeId Episode id
     * @param {boolean} finish Episode collection finish.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async updateCollectionEpisodeFinish(
      episodeId: number,
      finish: boolean,
      options?: AxiosRequestConfig
    ): Promise<
      (axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>
    > {
      const localVarAxiosArgs =
        await localVarAxiosParamCreator.updateCollectionEpisodeFinish(
          episodeId,
          finish,
          options
        );
      return createRequestFunction(
        localVarAxiosArgs,
        globalAxios,
        BASE_PATH,
        configuration
      );
    },
  };
};

/**
 * V1alpha1CollectionEpisodeApi - factory interface
 * @export
 */
export const V1alpha1CollectionEpisodeApiFactory = function (
  configuration?: Configuration,
  basePath?: string,
  axios?: AxiosInstance
) {
  const localVarFp = V1alpha1CollectionEpisodeApiFp(configuration);
  return {
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteCollectionEpisode(
      requestParameters: V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<EpisodeCollection> {
      return localVarFp
        .deleteCollectionEpisode(requestParameters.episodeId, options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    findCollectionEpisode(
      requestParameters: V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<EpisodeCollection> {
      return localVarFp
        .findCollectionEpisode(requestParameters.episodeId, options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    findCollectionEpisodesByUserIdAndSubjectId(
      requestParameters: V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<Array<EpisodeCollection>> {
      return localVarFp
        .findCollectionEpisodesByUserIdAndSubjectId(
          requestParameters.subjectId,
          options
        )
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    saveCollectionEpisode(
      requestParameters: V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<EpisodeCollection> {
      return localVarFp
        .saveCollectionEpisode(requestParameters.episodeId, options)
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    updateCollectionEpisode(
      requestParameters: V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<void> {
      return localVarFp
        .updateCollectionEpisode(
          requestParameters.episodeId,
          requestParameters.progress,
          requestParameters.duration,
          options
        )
        .then((request) => request(axios, basePath));
    },
    /**
     *
     * @param {V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    updateCollectionEpisodeFinish(
      requestParameters: V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest,
      options?: AxiosRequestConfig
    ): AxiosPromise<void> {
      return localVarFp
        .updateCollectionEpisodeFinish(
          requestParameters.episodeId,
          requestParameters.finish,
          options
        )
        .then((request) => request(axios, basePath));
    },
  };
};

/**
 * Request parameters for deleteCollectionEpisode operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest
 */
export interface V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest {
  /**
   * Episode id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiDeleteCollectionEpisode
   */
  readonly episodeId: number;
}

/**
 * Request parameters for findCollectionEpisode operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest
 */
export interface V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest {
  /**
   * Episode id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiFindCollectionEpisode
   */
  readonly episodeId: number;
}

/**
 * Request parameters for findCollectionEpisodesByUserIdAndSubjectId operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest
 */
export interface V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest {
  /**
   * Subject id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectId
   */
  readonly subjectId: number;
}

/**
 * Request parameters for saveCollectionEpisode operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest
 */
export interface V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest {
  /**
   * Episode id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiSaveCollectionEpisode
   */
  readonly episodeId: number;
}

/**
 * Request parameters for updateCollectionEpisode operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest
 */
export interface V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest {
  /**
   * Episode id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiUpdateCollectionEpisode
   */
  readonly episodeId: number;

  /**
   * Episode collection progress, unit is milliseconds.
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiUpdateCollectionEpisode
   */
  readonly progress: number;

  /**
   * Episode collection duration, unit is milliseconds.
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiUpdateCollectionEpisode
   */
  readonly duration?: number;
}

/**
 * Request parameters for updateCollectionEpisodeFinish operation in V1alpha1CollectionEpisodeApi.
 * @export
 * @interface V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest
 */
export interface V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest {
  /**
   * Episode id
   * @type {number}
   * @memberof V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinish
   */
  readonly episodeId: number;

  /**
   * Episode collection finish.
   * @type {boolean}
   * @memberof V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinish
   */
  readonly finish: boolean;
}

/**
 * V1alpha1CollectionEpisodeApi - object-oriented interface
 * @export
 * @class V1alpha1CollectionEpisodeApi
 * @extends {BaseAPI}
 */
export class V1alpha1CollectionEpisodeApi extends BaseAPI {
  /**
   *
   * @param {V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public deleteCollectionEpisode(
    requestParameters: V1alpha1CollectionEpisodeApiDeleteCollectionEpisodeRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .deleteCollectionEpisode(requestParameters.episodeId, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @param {V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public findCollectionEpisode(
    requestParameters: V1alpha1CollectionEpisodeApiFindCollectionEpisodeRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .findCollectionEpisode(requestParameters.episodeId, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @param {V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public findCollectionEpisodesByUserIdAndSubjectId(
    requestParameters: V1alpha1CollectionEpisodeApiFindCollectionEpisodesByUserIdAndSubjectIdRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .findCollectionEpisodesByUserIdAndSubjectId(
        requestParameters.subjectId,
        options
      )
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @param {V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public saveCollectionEpisode(
    requestParameters: V1alpha1CollectionEpisodeApiSaveCollectionEpisodeRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .saveCollectionEpisode(requestParameters.episodeId, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @param {V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public updateCollectionEpisode(
    requestParameters: V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .updateCollectionEpisode(
        requestParameters.episodeId,
        requestParameters.progress,
        requestParameters.duration,
        options
      )
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   *
   * @param {V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest} requestParameters Request parameters.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof V1alpha1CollectionEpisodeApi
   */
  public updateCollectionEpisodeFinish(
    requestParameters: V1alpha1CollectionEpisodeApiUpdateCollectionEpisodeFinishRequest,
    options?: AxiosRequestConfig
  ) {
    return V1alpha1CollectionEpisodeApiFp(this.configuration)
      .updateCollectionEpisodeFinish(
        requestParameters.episodeId,
        requestParameters.finish,
        options
      )
      .then((request) => request(this.axios, this.basePath));
  }
}
