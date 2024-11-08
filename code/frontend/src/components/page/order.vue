<template>
    <div>
        <app-head></app-head>
        <app-body>
            <div class="order-page-container">
                <div class="idle-info-container" @click="toDetails(orderInfo.idleItem.id)">
                    <el-image style="width: 150px; height: 150px" :src="orderInfo.idleItem.imgUrl" fit="cover" error="No image"> </el-image>
                    <div class="idle-info-title">
                        {{ orderInfo.userId == userId ? 'Purchased' : 'Sold' }}：{{ orderInfo.idleItem.idleName }}
                    </div>
                    <div class="idle-info-price">${{ orderInfo.orderPrice }}</div>
                </div>
                <div
                    class="address-container"
                    @click.stop="selectAddressDialog"
                    :style="orderInfo.userId == userId && orderInfo.orderStatus === 0 ? 'cursor: pointer;' : ''"
                >
                    <div class="address-title">
                        Offline Transaction Address: {{ addressInfo.consigneeName }} {{ addressInfo.consigneePhone }}
                    </div>
                    <div class="address-details">{{ addressInfo.detailAddress }}</div>
                    <el-button v-if="!addressInfo.detailAddress" @click.stop="selectAddressDialog" type="primary" plain>
                        Select Address</el-button
                    >
                </div>
                <el-dialog title="Select Address" :visible.sync="addressDialogVisible" width="800px">
                    <el-table
                        stripe
                        empty-text="No address information, please add an address in the personal center first"
                        :data="addressData"
                        style="width: 100%"
                    >
                        <el-table-column prop="consigneeName" label="Recipient Name" width="120"> </el-table-column>
                        <el-table-column prop="consigneePhone" label="Phone Number" width="140"> </el-table-column>
                        <el-table-column prop="detailAddressText" label="Address"> </el-table-column>
                        <el-table-column label=" " width="120">
                            <template slot-scope="scope">
                                <el-button size="mini" @click="selectAddress(scope.$index, scope.row)">Select</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-dialog>
                <div class="order-info-container">
                    <div class="order-info-title">Order Information ({{ orderStatus[orderInfo.orderStatus] }}):</div>
                    <div class="order-info-item">Order Number: {{ orderInfo.orderNumber }}</div>
                    <div class="order-info-item">Payment Status: {{ orderInfo.paymentStatus === 1 ? 'Paid' : 'Unpaid' }}</div>
                    <div class="order-info-item">Payment Method: {{ orderInfo.paymentWay }}</div>
                    <div class="order-info-item">
                        Creation Time: {{ orderInfo.createTime.substring(0, 10) + ' ' + orderInfo.createTime.substring(11, 19) }}
                    </div>
                    <!-- <div class="order-info-item">
                        Payment Time:
                        {{
                            orderInfo.paymentTime
                                ? orderInfo.paymentTime.substring(0, 10) + ' ' + orderInfo.paymentTime.substring(11, 19)
                                : ''
                        }}
                    </div> -->
                </div>
                <el-dialog title="Prompt" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
                    <span
                        >Please click 'Confirm' to put the product on hold. Reminder: the transaction will happen offline. Be careful.</span
                    >
                    <div class="block"></div>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="putOnHold">Confirm</el-button>
                    </span>
                </el-dialog>

                <div class="menu">
                    <el-button
                        v-if="userId == orderInfo.userId && orderInfo.orderStatus === 0"
                        type="danger"
                        plain
                        @click="changeOrderStatus(4, orderInfo)"
                        >Cancel Order</el-button
                    >
                    <!--
                    <el-button
                        v-if="userId == orderInfo.userId && orderInfo.orderStatus === 0"
                        type="primary"
                        plain
                        @click="changeOrderStatus(1, orderInfo)"
                        >Confirm</el-button
                    >
                    -->
                    <el-button
                        v-if="userId == orderInfo.idleItem.userId && orderInfo.orderStatus === 0"
                        type="primary"
                        plain
                        @click="changeOrderStatus(1, orderInfo)"
                        >On Hold</el-button
                    >
                    <el-button
                        v-if="userId == orderInfo.userId && orderInfo.orderStatus === 1"
                        type="primary"
                        plain
                        @click="changeOrderStatus(2, orderInfo)"
                        >Completed</el-button
                    >
                </div>
            </div>
            <app-foot></app-foot>
        </app-body>
    </div>
</template>

<script>
import AppHead from '../common/AppHeader.vue';
import AppBody from '../common/AppPageBody.vue';
import AppFoot from '../common/AppFoot.vue';

export default {
    name: 'order',
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            dialogVisible: false,
            addressDialogVisible: false,
            addressData: [],
            orderStatus: ['Pending', 'On Hold', 'Completed', 'Canceled'],
            orderInfo: {
                createTime: '',
                id: 0,
                idleId: 0,
                idleItem: {
                    id: '',
                    idleName: '',
                    idleDetails: '',
                    pictureList: [],
                    idlePrice: 0,
                    idlePlace: '',
                    idleLabel: '',
                    idleStatus: -1,
                    userId: ''
                },
                orderNumber: '',
                orderPrice: 0,
                orderStatus: 0,
                paymentStatus: 0,
                paymentTime: '',
                paymentWay: '',
                userId: 0
            },
            addressInfo: {
                id: '',
                update: false,
                consigneeName: '',
                consigneePhone: '',
                detailAddress: ''
            },
            userId: ''
        };
    },
    created() {
        this.userId = this.getCookie('shUserId');
        console.log('userId', this.userId, this.getCookie('shUserId'));
        let orderId = this.$route.query.id;
        console.log(orderId);
        this.$api
            .getOrder({
                id: orderId
            })
            .then((res) => {
                console.log(res);
                if (res.status_code === 1) {
                    if (res.data.idleItem) {
                        let imgList = JSON.parse(res.data.idleItem.pictureList);
                        if (imgList.length > 0) {
                            res.data.idleItem.imgUrl = imgList[0];
                        } else {
                            res.data.idleItem.imgUrl = '';
                        }
                    } else {
                        res.data.idleItem = {
                            idleName: '',
                            imgUrl: ''
                        };
                    }
                    this.orderInfo = res.data;
                    this.$api
                        .getOrderAddress({
                            orderId: this.orderInfo.id
                        })
                        .then((res) => {
                            if (res.data) {
                                this.addressInfo = res.data;
                                this.addressInfo.update = true;
                            } else {
                                this.getAddressData();
                            }
                        });
                }
            });
    },
    methods: {
        putOnHold() {
            this.orderInfo.orderStatus = 1;
            this.orderInfo.paymentStatus = 1;
            this.dialogVisible = false;
            this.changeOrderStatus(1, this.orderInfo);
        },
        handleClose() {
            this.dialogVisible = false;
        },
        getCookie(cname) {
            var name = cname + '=';
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i].trim();
                if (c.indexOf(name) === 0) return c.substring(name.length, c.length);
            }
            return '0';
        },
        toDetails(id) {
            this.$router.replace({
                path: 'details',
                query: {
                    id: id
                }
            });
        },
        selectAddressDialog() {
            if (this.orderInfo.userId == this.userId && this.orderInfo.orderStatus === 0) {
                this.addressDialogVisible = true;
                if (this.addressData.length === 0) {
                    this.getAddressData();
                }
            }
        },
        getAddressData() {
            this.$api.getAddress().then((res) => {
                if (res.status_code === 1) {
                    let data = res.data;
                    for (let i = 0; i < data.length; i++) {
                        data[i].detailAddressText = data[i].provinceName + data[i].cityName + data[i].regionName + data[i].detailAddress;
                    }
                    console.log(data);
                    this.addressData = data;
                    if (!this.addressInfo.update) {
                        for (let i = 0; i < data.length; i++) {
                            if (data[i].defaultFlag) {
                                this.selectAddress(i, data[i]);
                            }
                        }
                    }
                }
            });
        },
        selectAddress(i, item) {
            this.addressDialogVisible = false;
            console.log(item, this.addressInfo);
            this.addressInfo.consigneeName = item.consigneeName;
            this.addressInfo.consigneePhone = item.consigneePhone;
            this.addressInfo.detailAddress = item.detailAddressText;
            if (this.addressInfo.update) {
                this.$api.updateOrderAddress({
                    id: this.addressInfo.id,
                    consigneeName: item.consigneeName,
                    consigneePhone: item.consigneePhone,
                    detailAddress: item.detailAddressText
                });
            } else {
                this.$api
                    .addOrderAddress({
                        orderId: this.orderInfo.id,
                        consigneeName: item.consigneeName,
                        consigneePhone: item.consigneePhone,
                        detailAddress: item.detailAddressText
                    })
                    .then((res) => {
                        if (res.status_code === 1) {
                            this.addressInfo.update = true;
                            this.addressInfo.id = res.data.id;
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
            }
        },
        buySuccess9() {
            this.dialogVisible = false;
        },
        changeOrderStatus(orderStatus, orderInfo) {
            if (orderStatus === 1) {
                console.log('Putting order on hold');
                if (!this.addressInfo.detailAddress) {
                    this.$message.error('Please select an address!');
                } else {
                    this.$confirm('Confirm putting the order on hold', 'Order Status', {
                        confirmButtonText: 'Confirm',
                        cancelButtonText: 'Cancel',
                        type: 'warning'
                    })
                        .then(() => {
                            this.dialogVisible = true;
                            setTimeout(() => {
                                this.$api
                                    .updateOrder({
                                        id: orderInfo.id,
                                        orderStatus: orderStatus,
                                        paymentStatus: 1,
                                        paymentWay: 'Cash'
                                    })
                                    .then((res) => {
                                        if (res.status_code === 1) {
                                            this.$message({
                                                message: 'Order is now on hold!',
                                                type: 'success'
                                            });
                                            this.orderInfo.orderStatus = orderStatus;
                                            this.orderInfo.paymentStatus = 1;
                                            this.orderInfo.paymentWay = 'Cash';
                                        }
                                    });
                            }, 2000);
                        })
                        .catch(() => {});
                }
            } else {
                this.$api
                    .updateOrder({
                        id: orderInfo.id,
                        orderStatus: orderStatus
                    })
                    .then((res) => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: 'Operation Successful!',
                                type: 'success'
                            });
                            this.orderInfo.orderStatus = orderStatus;
                        }
                    });
            }
        }
    }
};
</script>

<style scoped>
.order-page-container {
    min-height: 85vh;
}

.idle-info-container {
    width: 100%;
    display: flex;
    border-bottom: 20px solid #f6f6f6;
    padding: 20px;
    cursor: pointer;
}

.idle-info-title {
    font-size: 18px;
    font-weight: 600;
    max-width: 750px;
    margin-left: 10px;
}

.idle-info-price {
    font-size: 18px;
    color: red;
    margin-left: 10px;
}

.address-container {
    min-height: 60px;
    padding: 20px;
    border-bottom: 20px solid #f6f6f6;
}

.address-title {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 10px;
}

.address-details {
    font-size: 16px;
    color: #444444;
}

.order-info-container {
    padding: 20px;
}

.order-info-item {
    margin: 10px 0;
    font-size: 14px;
    color: #444444;
}

.menu {
    margin-left: 20px;
}
</style>
