"use strict";(self["webpackChunkvue_antd_pro"]=self["webpackChunkvue_antd_pro"]||[]).push([[635],{28209:function(t,e,r){r.r(e),r.d(e,{default:function(){return g}});var o=function(){var t=this,e=t._self._c;return e("page-header-wrapper",{attrs:{title:!1}},[e("div",{staticClass:"container"},[e("a-tabs",{attrs:{type:"line"},on:{change:t.selectTabChange}},[e("a-tab-pane",{key:"common",staticClass:"tab-content-pane",attrs:{tab:"常规设置"}},[e("a-form-model",{attrs:{model:t.common}},[e("a-form-model-item",{attrs:{label:"标题"}},[e("a-input",{model:{value:t.common.title,callback:function(e){t.$set(t.common,"title",e)},expression:"common.title"}})],1),e("a-form-model-item",{attrs:{label:"地址"}},[e("a-input",{model:{value:t.common.address,callback:function(e){t.$set(t.common,"address",e)},expression:"common.address"}})],1),e("a-form-model-item",{attrs:{label:"Logo"}},[e("a-input",{model:{value:t.common.logo,callback:function(e){t.$set(t.common,"logo",e)},expression:"common.logo"}})],1),e("a-form-model-item",{attrs:{label:"Favicon"}},[e("a-input",{model:{value:t.common.favicon,callback:function(e){t.$set(t.common,"favicon",e)},expression:"common.favicon"}})],1),e("a-form-model-item",{attrs:{label:"页脚信息"}},[e("a-input",{attrs:{type:"textarea"},model:{value:t.common.footer,callback:function(e){t.$set(t.common,"footer",e)},expression:"common.footer"}})],1),e("a-form-model-item",[e("a-button",{attrs:{type:"primary"},on:{click:t.saveCommonOtpion}},[t._v(" 保存常规设置 ")])],1)],1)],1),e("a-tab-pane",{key:"seo",staticClass:"tab-content-pane",attrs:{tab:"SEO设置"}},[e("a-form-model",{attrs:{model:t.seo}},[e("a-form-model-item",{attrs:{label:"屏蔽搜索引擎"}},[e("a-input",{model:{value:t.seo.hideForSearchEngine,callback:function(e){t.$set(t.seo,"hideForSearchEngine",e)},expression:"seo.hideForSearchEngine"}})],1),e("a-form-model-item",{attrs:{label:"关键词"}},[e("a-input",{model:{value:t.seo.keywords,callback:function(e){t.$set(t.seo,"keywords",e)},expression:"seo.keywords"}})],1),e("a-form-model-item",{attrs:{label:"站点描述"}},[e("a-input",{attrs:{type:"textarea"},model:{value:t.seo.siteDescription,callback:function(e){t.$set(t.seo,"siteDescription",e)},expression:"seo.siteDescription"}})],1),e("a-form-model-item",[e("a-button",{attrs:{type:"primary"},on:{click:t.saveSeoOption}},[t._v(" 保存SEO设置 ")])],1)],1)],1),e("a-tab-pane",{key:"file",staticClass:"tab-content-pane",attrs:{tab:"文件设置"}},[e("a-form-model",{attrs:{model:t.file}},[e("a-form-model-item",{attrs:{label:"存储位置"}},[e("a-input",{model:{value:t.file.placeSelect,callback:function(e){t.$set(t.file,"placeSelect",e)},expression:"file.placeSelect"}})],1),e("a-form-model-item",[e("a-button",{attrs:{type:"primary"},on:{click:t.saveFileOption}},[t._v(" 保存文件设置 ")])],1)],1)],1),e("a-tab-pane",{key:"network",staticClass:"tab-content-pane",attrs:{tab:"第三方设置"}},[e("a-form-model",{attrs:{model:t.thirdParty}},[e("a-form-model-item",{attrs:{label:"bgmTvAPI前缀"}},[e("a-input",{model:{value:t.thirdParty.bangumiApiBase,callback:function(e){t.$set(t.thirdParty,"bangumiApiBase",e)},expression:"thirdParty.bangumiApiBase"}})],1),e("a-form-model-item",{attrs:{label:"bgmTvAPI条目"}},[e("a-input",{model:{value:t.thirdParty.bangumiApiSubjects,callback:function(e){t.$set(t.thirdParty,"bangumiApiSubjects",e)},expression:"thirdParty.bangumiApiSubjects"}})],1),e("a-form-model-item",[e("a-button",{attrs:{type:"primary"},on:{click:t.saveThirdPartyOption}},[t._v(" 保存第三方设置 ")])],1)],1)],1),e("a-tab-pane",{key:"other",staticClass:"tab-content-pane",attrs:{tab:"其它设置"}},[e("a-form-model",{attrs:{model:t.other}},[e("a-form-model-item",{attrs:{label:"自定义全局 head"}},[e("a-input",{attrs:{type:"textarea"},model:{value:t.other.customerGlobalHeader,callback:function(e){t.$set(t.other,"customerGlobalHeader",e)},expression:"other.customerGlobalHeader"}})],1),e("a-form-model-item",{attrs:{label:"统计代码"}},[e("a-input",{attrs:{type:"textarea"},model:{value:t.other.statisticsCode,callback:function(e){t.$set(t.other,"statisticsCode",e)},expression:"other.statisticsCode"}})],1),e("a-form-model-item",[e("a-button",{attrs:{type:"primary"},on:{click:t.saveOtherOption}},[t._v(" 保存其它设置 ")])],1)],1)],1)],1)],1)])},n=[],a=r(6835),i=r(48534),c=(r(41539),r(54747),r(46945)),s={findOptionModelList:"/option/model/list",saveCommonOptionModel:"/option/model/common",saveSeoOptionModel:"/option/model/seo",saveFileOptionModel:"/option/model/file",saveThirdPartyOption:"/option/model/thirdparty",saveOtherOptionModel:"/option/model/other"};function l(){return(0,c.ZP)({url:s.findOptionModelList,method:"get"})}function u(t){return(0,c.ZP)({url:s.saveCommonOptionModel,method:"post",data:t})}function f(t){return(0,c.ZP)({url:s.saveSeoOptionModel,method:"post",data:t})}function m(t){return(0,c.ZP)({url:s.saveFileOptionModel,method:"post",data:t})}function p(t){return(0,c.ZP)({url:s.saveOtherOptionModel,method:"post",data:t})}function h(t){return(0,c.ZP)({url:s.saveThirdPartyOption,method:"post",data:t})}var d={data:function(){return{options:[],common:{},seo:{},file:{},thirdParty:{},other:{}}},created:function(){this.findOptionModels()},methods:{selectTabChange:function(t){},findOptionModels:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){var r,o;return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,l();case 2:r=e.sent,o=r.result,t.options=o,t.options.forEach((function(e){var r=e.category;"common"===r&&(t.common=e),"seo"===r&&(t.seo=e),"file"===r&&(t.file=e),"file"===r&&(t.file=e),"thirdparty"===r&&(t.thirdParty=e)})),t.$log.debug("network",t.network);case 7:case"end":return e.stop()}}),e)})))()},saveCommonOtpion:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,u(t.common);case 2:t.$message.info("更新成功");case 3:case"end":return e.stop()}}),e)})))()},saveFileOption:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,m(t.file);case 2:t.$message.info("更新成功");case 3:case"end":return e.stop()}}),e)})))()},saveSeoOption:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,f(t.seo);case 2:t.$message.info("更新成功");case 3:case"end":return e.stop()}}),e)})))()},saveOtherOption:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,p(t.other);case 2:t.$message.info("更新成功");case 3:case"end":return e.stop()}}),e)})))()},saveThirdPartyOption:function(){var t=this;return(0,i.Z)((0,a.Z)().mark((function e(){return(0,a.Z)().wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,h(t.thirdParty);case 2:t.$message.info("更新成功");case 3:case"end":return e.stop()}}),e)})))()}}},v=d,y=r(1001),b=(0,y.Z)(v,o,n,!1,null,"e489f600",null),g=b.exports},48534:function(t,e,r){r.d(e,{Z:function(){return n}});r(41539);function o(t,e,r,o,n,a,i){try{var c=t[a](i),s=c.value}catch(l){return void r(l)}c.done?e(s):Promise.resolve(s).then(o,n)}function n(t){return function(){var e=this,r=arguments;return new Promise((function(n,a){var i=t.apply(e,r);function c(t){o(i,n,a,c,s,"next",t)}function s(t){o(i,n,a,c,s,"throw",t)}c(void 0)}))}}},6835:function(t,e,r){r.d(e,{Z:function(){return n}});r(82526),r(41817),r(41539),r(32165),r(78783),r(33948),r(72443),r(39341),r(73706),r(10408),r(30489),r(54747),r(68309),r(68304),r(47042);function o(t){return o="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},o(t)}function n(){
/*! regenerator-runtime -- Copyright (c) 2014-present, Facebook, Inc. -- license (MIT): https://github.com/facebook/regenerator/blob/main/LICENSE */
n=function(){return t};var t={},e=Object.prototype,r=e.hasOwnProperty,a="function"==typeof Symbol?Symbol:{},i=a.iterator||"@@iterator",c=a.asyncIterator||"@@asyncIterator",s=a.toStringTag||"@@toStringTag";function l(t,e,r){return Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}),t[e]}try{l({},"")}catch(Z){l=function(t,e,r){return t[e]=r}}function u(t,e,r,o){var n=e&&e.prototype instanceof p?e:p,a=Object.create(n.prototype),i=new P(o||[]);return a._invoke=function(t,e,r){var o="suspendedStart";return function(n,a){if("executing"===o)throw new Error("Generator is already running");if("completed"===o){if("throw"===n)throw a;return S()}for(r.method=n,r.arg=a;;){var i=r.delegate;if(i){var c=k(i,r);if(c){if(c===m)continue;return c}}if("next"===r.method)r.sent=r._sent=r.arg;else if("throw"===r.method){if("suspendedStart"===o)throw o="completed",r.arg;r.dispatchException(r.arg)}else"return"===r.method&&r.abrupt("return",r.arg);o="executing";var s=f(t,e,r);if("normal"===s.type){if(o=r.done?"completed":"suspendedYield",s.arg===m)continue;return{value:s.arg,done:r.done}}"throw"===s.type&&(o="completed",r.method="throw",r.arg=s.arg)}}}(t,r,i),a}function f(t,e,r){try{return{type:"normal",arg:t.call(e,r)}}catch(Z){return{type:"throw",arg:Z}}}t.wrap=u;var m={};function p(){}function h(){}function d(){}var v={};l(v,i,(function(){return this}));var y=Object.getPrototypeOf,b=y&&y(y(E([])));b&&b!==e&&r.call(b,i)&&(v=b);var g=d.prototype=p.prototype=Object.create(v);function w(t){["next","throw","return"].forEach((function(e){l(t,e,(function(t){return this._invoke(e,t)}))}))}function x(t,e){function n(a,i,c,s){var l=f(t[a],t,i);if("throw"!==l.type){var u=l.arg,m=u.value;return m&&"object"==o(m)&&r.call(m,"__await")?e.resolve(m.__await).then((function(t){n("next",t,c,s)}),(function(t){n("throw",t,c,s)})):e.resolve(m).then((function(t){u.value=t,c(u)}),(function(t){return n("throw",t,c,s)}))}s(l.arg)}var a;this._invoke=function(t,r){function o(){return new e((function(e,o){n(t,r,e,o)}))}return a=a?a.then(o,o):o()}}function k(t,e){var r=t.iterator[e.method];if(void 0===r){if(e.delegate=null,"throw"===e.method){if(t.iterator["return"]&&(e.method="return",e.arg=void 0,k(t,e),"throw"===e.method))return m;e.method="throw",e.arg=new TypeError("The iterator does not provide a 'throw' method")}return m}var o=f(r,t.iterator,e.arg);if("throw"===o.type)return e.method="throw",e.arg=o.arg,e.delegate=null,m;var n=o.arg;return n?n.done?(e[t.resultName]=n.value,e.next=t.nextLoc,"return"!==e.method&&(e.method="next",e.arg=void 0),e.delegate=null,m):n:(e.method="throw",e.arg=new TypeError("iterator result is not an object"),e.delegate=null,m)}function O(t){var e={tryLoc:t[0]};1 in t&&(e.catchLoc=t[1]),2 in t&&(e.finallyLoc=t[2],e.afterLoc=t[3]),this.tryEntries.push(e)}function L(t){var e=t.completion||{};e.type="normal",delete e.arg,t.completion=e}function P(t){this.tryEntries=[{tryLoc:"root"}],t.forEach(O,this),this.reset(!0)}function E(t){if(t){var e=t[i];if(e)return e.call(t);if("function"==typeof t.next)return t;if(!isNaN(t.length)){var o=-1,n=function e(){for(;++o<t.length;)if(r.call(t,o))return e.value=t[o],e.done=!1,e;return e.value=void 0,e.done=!0,e};return n.next=n}}return{next:S}}function S(){return{value:void 0,done:!0}}return h.prototype=d,l(g,"constructor",d),l(d,"constructor",h),h.displayName=l(d,s,"GeneratorFunction"),t.isGeneratorFunction=function(t){var e="function"==typeof t&&t.constructor;return!!e&&(e===h||"GeneratorFunction"===(e.displayName||e.name))},t.mark=function(t){return Object.setPrototypeOf?Object.setPrototypeOf(t,d):(t.__proto__=d,l(t,s,"GeneratorFunction")),t.prototype=Object.create(g),t},t.awrap=function(t){return{__await:t}},w(x.prototype),l(x.prototype,c,(function(){return this})),t.AsyncIterator=x,t.async=function(e,r,o,n,a){void 0===a&&(a=Promise);var i=new x(u(e,r,o,n),a);return t.isGeneratorFunction(r)?i:i.next().then((function(t){return t.done?t.value:i.next()}))},w(g),l(g,s,"Generator"),l(g,i,(function(){return this})),l(g,"toString",(function(){return"[object Generator]"})),t.keys=function(t){var e=[];for(var r in t)e.push(r);return e.reverse(),function r(){for(;e.length;){var o=e.pop();if(o in t)return r.value=o,r.done=!1,r}return r.done=!0,r}},t.values=E,P.prototype={constructor:P,reset:function(t){if(this.prev=0,this.next=0,this.sent=this._sent=void 0,this.done=!1,this.delegate=null,this.method="next",this.arg=void 0,this.tryEntries.forEach(L),!t)for(var e in this)"t"===e.charAt(0)&&r.call(this,e)&&!isNaN(+e.slice(1))&&(this[e]=void 0)},stop:function(){this.done=!0;var t=this.tryEntries[0].completion;if("throw"===t.type)throw t.arg;return this.rval},dispatchException:function(t){if(this.done)throw t;var e=this;function o(r,o){return i.type="throw",i.arg=t,e.next=r,o&&(e.method="next",e.arg=void 0),!!o}for(var n=this.tryEntries.length-1;n>=0;--n){var a=this.tryEntries[n],i=a.completion;if("root"===a.tryLoc)return o("end");if(a.tryLoc<=this.prev){var c=r.call(a,"catchLoc"),s=r.call(a,"finallyLoc");if(c&&s){if(this.prev<a.catchLoc)return o(a.catchLoc,!0);if(this.prev<a.finallyLoc)return o(a.finallyLoc)}else if(c){if(this.prev<a.catchLoc)return o(a.catchLoc,!0)}else{if(!s)throw new Error("try statement without catch or finally");if(this.prev<a.finallyLoc)return o(a.finallyLoc)}}}},abrupt:function(t,e){for(var o=this.tryEntries.length-1;o>=0;--o){var n=this.tryEntries[o];if(n.tryLoc<=this.prev&&r.call(n,"finallyLoc")&&this.prev<n.finallyLoc){var a=n;break}}a&&("break"===t||"continue"===t)&&a.tryLoc<=e&&e<=a.finallyLoc&&(a=null);var i=a?a.completion:{};return i.type=t,i.arg=e,a?(this.method="next",this.next=a.finallyLoc,m):this.complete(i)},complete:function(t,e){if("throw"===t.type)throw t.arg;return"break"===t.type||"continue"===t.type?this.next=t.arg:"return"===t.type?(this.rval=this.arg=t.arg,this.method="return",this.next="end"):"normal"===t.type&&e&&(this.next=e),m},finish:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.finallyLoc===t)return this.complete(r.completion,r.afterLoc),L(r),m}},catch:function(t){for(var e=this.tryEntries.length-1;e>=0;--e){var r=this.tryEntries[e];if(r.tryLoc===t){var o=r.completion;if("throw"===o.type){var n=o.arg;L(r)}return n}}throw new Error("illegal catch attempt")},delegateYield:function(t,e,r){return this.delegate={iterator:E(t),resultName:e,nextLoc:r},"next"===this.method&&(this.arg=void 0),m}},t}}}]);