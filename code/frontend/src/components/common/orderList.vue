<template>
    <div class="main-border">
        <span class="app-title">
            <el-input placeholder="Order number..." v-model="searchValue" @keyup.enter.native="searchIdle">
                <el-button slot="append" icon="el-icon-search" @click="searchIdle"></el-button>
            </el-input>
        </span>
        <el-table :data="Order" stripe style="width: 100%; color: #5a5c61">
            <el-table-column prop="orderNumber" label="orderNumber" show-overflow-tooltip width="200"> </el-table-column>
            <el-table-column prop="idleItem.idleName" label="itemName" show-overflow-tooltip> </el-table-column>
            <el-table-column prop="orderPrice" label="orderPrice" show-overflow-tooltip min-width="100" width="100"> </el-table-column>
            <el-table-column prop="createTime" label="createTime" show-overflow-tooltip min-width="150" width="200"> </el-table-column>
            <el-table-column label="orderStatus" width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                    <div>{{ orderStatus[scope.row.orderStatus] }}</div>
                </template>
            </el-table-column>
            <el-table-column label="paymentStatus" width="100" show-overflow-tooltip class="cell el-tooltip">
                <template slot-scope="scope">
                    <div>{{ paymentStatus[scope.row.paymentStatus] }}</div>
                </template>
            </el-table-column>
            <el-table-column prop="paymentWay" label="paymentWay" width="100" show-overflow-tooltip> </el-table-column>
            <el-table-column prop="paymentTime" label="paymentTime" show-overflow-tooltip> </el-table-column>
            <el-table-column label="operation">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="deleteOrder(scope.$index)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="nowPage"
                :page-size="8"
                background
                layout="prev, pager, next"
                :total="total"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: 'orderList',
    created() {
        this.getOrder();
    },
    methods: {
        getOrder() {
            this.$api
                .getOrderList({
                    page: this.nowPage,
                    nums: 8
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.Order = res.data.list;
                        this.total = res.data.count;
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        deleteOrder(index) {
            this.$api
                .deleteOrder({
                    id: this.Order[index].id
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.getOrder();
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        handleCurrentChange(val) {
            this.nowPage = val;
            this.getOrder();
        },
        searchIdle() {
            this.$api
                .queryOrder({
                    page: this.nowPage,
                    nums: 8,
                    searchValue: this.searchValue
                })
                .then((res) => {
                    console.log(res);
                    if (res.status_code == 1) {
                        this.Order = res.data.list;
                        this.total = res.data.count;
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        }
    },
    data() {
        return {
            mode: 1,
            nowPage: 1,
            total: 0,
            paymentStatus: ['Unpaid', 'Paid'],
            orderStatus: ['Pending', 'On hold', 'Complete', 'Cancelled'],
            Order: [],
            searchValue: ''
        };
    }
};
</script>

<style scoped>
.main-border {
    background-color: #fff;
    padding: 10px 30px;
    box-shadow: 0 1px 15px -6px rgba(0, 0, 0, 0.5);
    border-radius: 5px;
}
.block {
    display: flex;
    justify-content: center;
    padding-top: 15px;
    padding-bottom: 10px;
    width: 100%;
}
</style>
