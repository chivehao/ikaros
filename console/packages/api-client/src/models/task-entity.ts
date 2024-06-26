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
 * @interface TaskEntity
 */
export interface TaskEntity {
  /**
   *
   * @type {number}
   * @memberof TaskEntity
   */
  id?: number;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  name?: string;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  status?: TaskEntityStatusEnum;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  createTime?: string;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  startTime?: string;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  endTime?: string;
  /**
   *
   * @type {number}
   * @memberof TaskEntity
   */
  total?: number;
  /**
   *
   * @type {number}
   * @memberof TaskEntity
   */
  index?: number;
  /**
   *
   * @type {string}
   * @memberof TaskEntity
   */
  failMessage?: string;
}

export const TaskEntityStatusEnum = {
  Create: "CREATE",
  Running: "RUNNING",
  Finish: "FINISH",
  Cancel: "CANCEL",
  Fail: "FAIL",
} as const;

export type TaskEntityStatusEnum =
  (typeof TaskEntityStatusEnum)[keyof typeof TaskEntityStatusEnum];
