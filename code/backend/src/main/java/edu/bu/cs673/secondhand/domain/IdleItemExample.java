package edu.bu.cs673.secondhand.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdleItemExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IdleItemExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdleNameIsNull() {
            addCriterion("idle_name is null");
            return (Criteria) this;
        }

        public Criteria andIdleNameIsNotNull() {
            addCriterion("idle_name is not null");
            return (Criteria) this;
        }

        public Criteria andIdleNameEqualTo(String value) {
            addCriterion("idle_name =", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameNotEqualTo(String value) {
            addCriterion("idle_name <>", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameGreaterThan(String value) {
            addCriterion("idle_name >", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameGreaterThanOrEqualTo(String value) {
            addCriterion("idle_name >=", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameLessThan(String value) {
            addCriterion("idle_name <", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameLessThanOrEqualTo(String value) {
            addCriterion("idle_name <=", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameLike(String value) {
            addCriterion("idle_name like", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameNotLike(String value) {
            addCriterion("idle_name not like", value, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameIn(List<String> values) {
            addCriterion("idle_name in", values, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameNotIn(List<String> values) {
            addCriterion("idle_name not in", values, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameBetween(String value1, String value2) {
            addCriterion("idle_name between", value1, value2, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleNameNotBetween(String value1, String value2) {
            addCriterion("idle_name not between", value1, value2, "idleName");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsIsNull() {
            addCriterion("idle_details is null");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsIsNotNull() {
            addCriterion("idle_details is not null");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsEqualTo(String value) {
            addCriterion("idle_details =", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsNotEqualTo(String value) {
            addCriterion("idle_details <>", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsGreaterThan(String value) {
            addCriterion("idle_details >", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsGreaterThanOrEqualTo(String value) {
            addCriterion("idle_details >=", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsLessThan(String value) {
            addCriterion("idle_details <", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsLessThanOrEqualTo(String value) {
            addCriterion("idle_details <=", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsLike(String value) {
            addCriterion("idle_details like", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsNotLike(String value) {
            addCriterion("idle_details not like", value, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsIn(List<String> values) {
            addCriterion("idle_details in", values, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsNotIn(List<String> values) {
            addCriterion("idle_details not in", values, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsBetween(String value1, String value2) {
            addCriterion("idle_details between", value1, value2, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andIdleDetailsNotBetween(String value1, String value2) {
            addCriterion("idle_details not between", value1, value2, "idleDetails");
            return (Criteria) this;
        }

        public Criteria andPictureListIsNull() {
            addCriterion("picture_list is null");
            return (Criteria) this;
        }

        public Criteria andPictureListIsNotNull() {
            addCriterion("picture_list is not null");
            return (Criteria) this;
        }

        public Criteria andPictureListEqualTo(String value) {
            addCriterion("picture_list =", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListNotEqualTo(String value) {
            addCriterion("picture_list <>", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListGreaterThan(String value) {
            addCriterion("picture_list >", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListGreaterThanOrEqualTo(String value) {
            addCriterion("picture_list >=", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListLessThan(String value) {
            addCriterion("picture_list <", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListLessThanOrEqualTo(String value) {
            addCriterion("picture_list <=", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListLike(String value) {
            addCriterion("picture_list like", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListNotLike(String value) {
            addCriterion("picture_list not like", value, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListIn(List<String> values) {
            addCriterion("picture_list in", values, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListNotIn(List<String> values) {
            addCriterion("picture_list not in", values, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListBetween(String value1, String value2) {
            addCriterion("picture_list between", value1, value2, "pictureList");
            return (Criteria) this;
        }

        public Criteria andPictureListNotBetween(String value1, String value2) {
            addCriterion("picture_list not between", value1, value2, "pictureList");
            return (Criteria) this;
        }

        public Criteria andIdlePriceIsNull() {
            addCriterion("idle_price is null");
            return (Criteria) this;
        }

        public Criteria andIdlePriceIsNotNull() {
            addCriterion("idle_price is not null");
            return (Criteria) this;
        }

        public Criteria andIdlePriceEqualTo(BigDecimal value) {
            addCriterion("idle_price =", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceNotEqualTo(BigDecimal value) {
            addCriterion("idle_price <>", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceGreaterThan(BigDecimal value) {
            addCriterion("idle_price >", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("idle_price >=", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceLessThan(BigDecimal value) {
            addCriterion("idle_price <", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("idle_price <=", value, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceIn(List<BigDecimal> values) {
            addCriterion("idle_price in", values, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceNotIn(List<BigDecimal> values) {
            addCriterion("idle_price not in", values, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("idle_price between", value1, value2, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("idle_price not between", value1, value2, "idlePrice");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceIsNull() {
            addCriterion("idle_place is null");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceIsNotNull() {
            addCriterion("idle_place is not null");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceEqualTo(String value) {
            addCriterion("idle_place =", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceNotEqualTo(String value) {
            addCriterion("idle_place <>", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceGreaterThan(String value) {
            addCriterion("idle_place >", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("idle_place >=", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceLessThan(String value) {
            addCriterion("idle_place <", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceLessThanOrEqualTo(String value) {
            addCriterion("idle_place <=", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceLike(String value) {
            addCriterion("idle_place like", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceNotLike(String value) {
            addCriterion("idle_place not like", value, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceIn(List<String> values) {
            addCriterion("idle_place in", values, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceNotIn(List<String> values) {
            addCriterion("idle_place not in", values, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceBetween(String value1, String value2) {
            addCriterion("idle_place between", value1, value2, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdlePlaceNotBetween(String value1, String value2) {
            addCriterion("idle_place not between", value1, value2, "idlePlace");
            return (Criteria) this;
        }

        public Criteria andIdleLabelIsNull() {
            addCriterion("idle_label is null");
            return (Criteria) this;
        }

        public Criteria andIdleLabelIsNotNull() {
            addCriterion("idle_label is not null");
            return (Criteria) this;
        }

        public Criteria andIdleLabelEqualTo(Integer value) {
            addCriterion("idle_label =", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelNotEqualTo(Integer value) {
            addCriterion("idle_label <>", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelGreaterThan(Integer value) {
            addCriterion("idle_label >", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelGreaterThanOrEqualTo(Integer value) {
            addCriterion("idle_label >=", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelLessThan(Integer value) {
            addCriterion("idle_label <", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelLessThanOrEqualTo(Integer value) {
            addCriterion("idle_label <=", value, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelIn(List<Integer> values) {
            addCriterion("idle_label in", values, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelNotIn(List<Integer> values) {
            addCriterion("idle_label not in", values, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelBetween(Integer value1, Integer value2) {
            addCriterion("idle_label between", value1, value2, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andIdleLabelNotBetween(Integer value1, Integer value2) {
            addCriterion("idle_label not between", value1, value2, "idleLabel");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIsNull() {
            addCriterion("release_time is null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIsNotNull() {
            addCriterion("release_time is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeEqualTo(Date value) {
            addCriterion("release_time =", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotEqualTo(Date value) {
            addCriterion("release_time <>", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThan(Date value) {
            addCriterion("release_time >", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("release_time >=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThan(Date value) {
            addCriterion("release_time <", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeLessThanOrEqualTo(Date value) {
            addCriterion("release_time <=", value, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeIn(List<Date> values) {
            addCriterion("release_time in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotIn(List<Date> values) {
            addCriterion("release_time not in", values, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeBetween(Date value1, Date value2) {
            addCriterion("release_time between", value1, value2, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andReleaseTimeNotBetween(Date value1, Date value2) {
            addCriterion("release_time not between", value1, value2, "releaseTime");
            return (Criteria) this;
        }

        public Criteria andIdleStatusIsNull() {
            addCriterion("idle_status is null");
            return (Criteria) this;
        }

        public Criteria andIdleStatusIsNotNull() {
            addCriterion("idle_status is not null");
            return (Criteria) this;
        }

        public Criteria andIdleStatusEqualTo(Byte value) {
            addCriterion("idle_status =", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusNotEqualTo(Byte value) {
            addCriterion("idle_status <>", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusGreaterThan(Byte value) {
            addCriterion("idle_status >", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("idle_status >=", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusLessThan(Byte value) {
            addCriterion("idle_status <", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusLessThanOrEqualTo(Byte value) {
            addCriterion("idle_status <=", value, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusIn(List<Byte> values) {
            addCriterion("idle_status in", values, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusNotIn(List<Byte> values) {
            addCriterion("idle_status not in", values, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusBetween(Byte value1, Byte value2) {
            addCriterion("idle_status between", value1, value2, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andIdleStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("idle_status not between", value1, value2, "idleStatus");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}