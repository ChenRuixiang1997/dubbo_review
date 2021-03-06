package com.self.learning.common.dto;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table orders
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Orders {
    /**
     * Database Column Remarks:
     *   订单自增id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.o_id
     *
     * @mbg.generated
     */
    private Long oId;

    /**
     * Database Column Remarks:
     *   订单号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.o_no
     *
     * @mbg.generated
     */
    private String oNo;

    /**
     * Database Column Remarks:
     *   订单状态(0.未付款)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.o_state
     *
     * @mbg.generated
     */
    private Integer oState;

    /**
     * Database Column Remarks:
     *   商品id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.g_id
     *
     * @mbg.generated
     */
    private String gId;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.createdDate
     *
     * @mbg.generated
     */
    private Date createddate;

    /**
     * Database Column Remarks:
     *   手机号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.phoneNum
     *
     * @mbg.generated
     */
    private String phonenum;

    /**
     * Database Column Remarks:
     *   是否失效(1.未失效)
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.invalid
     *
     * @mbg.generated
     */
    private Integer invalid;

    /**
     * Database Column Remarks:
     *   商品数量
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.good_num
     *
     * @mbg.generated
     */
    private Integer goodNum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.o_id
     *
     * @return the value of orders.o_id
     *
     * @mbg.generated
     */
    public Long getoId() {
        return oId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.o_id
     *
     * @param oId the value for orders.o_id
     *
     * @mbg.generated
     */
    public void setoId(Long oId) {
        this.oId = oId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.o_no
     *
     * @return the value of orders.o_no
     *
     * @mbg.generated
     */
    public String getoNo() {
        return oNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.o_no
     *
     * @param oNo the value for orders.o_no
     *
     * @mbg.generated
     */
    public void setoNo(String oNo) {
        this.oNo = oNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.o_state
     *
     * @return the value of orders.o_state
     *
     * @mbg.generated
     */
    public Integer getoState() {
        return oState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.o_state
     *
     * @param oState the value for orders.o_state
     *
     * @mbg.generated
     */
    public void setoState(Integer oState) {
        this.oState = oState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.g_id
     *
     * @return the value of orders.g_id
     *
     * @mbg.generated
     */
    public String getgId() {
        return gId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.g_id
     *
     * @param gId the value for orders.g_id
     *
     * @mbg.generated
     */
    public void setgId(String gId) {
        this.gId = gId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.createdDate
     *
     * @return the value of orders.createdDate
     *
     * @mbg.generated
     */
    public Date getCreateddate() {
        return createddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.createdDate
     *
     * @param createddate the value for orders.createdDate
     *
     * @mbg.generated
     */
    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.phoneNum
     *
     * @return the value of orders.phoneNum
     *
     * @mbg.generated
     */
    public String getPhonenum() {
        return phonenum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.phoneNum
     *
     * @param phonenum the value for orders.phoneNum
     *
     * @mbg.generated
     */
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.invalid
     *
     * @return the value of orders.invalid
     *
     * @mbg.generated
     */
    public Integer getInvalid() {
        return invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.invalid
     *
     * @param invalid the value for orders.invalid
     *
     * @mbg.generated
     */
    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.good_num
     *
     * @return the value of orders.good_num
     *
     * @mbg.generated
     */
    public Integer getGoodNum() {
        return goodNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.good_num
     *
     * @param goodNum the value for orders.good_num
     *
     * @mbg.generated
     */
    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }
}