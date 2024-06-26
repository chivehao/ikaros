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

/**
 *
 * @export
 * @interface Authority
 */
export interface Authority {
  /**
   *
   * @type {number}
   * @memberof Authority
   */
  id?: number;
  /**
   *
   * @type {boolean}
   * @memberof Authority
   */
  allow?: boolean;
  /**
   *
   * @type {string}
   * @memberof Authority
   */
  type?: AuthorityTypeEnum;
  /**
   *
   * @type {string}
   * @memberof Authority
   */
  target?: string;
  /**
   *
   * @type {string}
   * @memberof Authority
   */
  authority?: string;
}

export const AuthorityTypeEnum = {
  All: "ALL",
  Api: "API",
  Apis: "APIS",
  Menu: "MENU",
  Url: "URL",
  Others: "OTHERS",
} as const;

export type AuthorityTypeEnum =
  (typeof AuthorityTypeEnum)[keyof typeof AuthorityTypeEnum];
