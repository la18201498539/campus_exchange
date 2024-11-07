<template>
    <div>
        <app-head :nickname-value="userInfo.nickname" :avatarValue="userInfo.avatar"></app-head>
        <app-body>
            <div v-show="!eidtAddress">
                <div class="user-info-container">
                    <div class="user-info-details">
                        <el-upload
                            action="http://47.252.36.46:8080/file"
                            :on-success="fileHandleSuccess"
                            :file-list="imgFileList"
                            accept="image/*"
                        >
                            <el-image
                                style="width: 120px; height: 120px; border-radius: 10px"
                                :src="userInfo.avatar"
                                fit="contain"
                            ></el-image>
                        </el-upload>
                        <div class="user-info-details-text">
                            <div class="user-info-details-text-nickname">{{ userInfo.nickname }}</div>
                            <div class="user-info-details-text-time">{{ userInfo.signInTime }} joined platform</div>
                            <div class="user-info-details-text-edit">
                                <el-button type="primary" plain @click="userInfoDialogVisible = true">Edit Personal Information</el-button>
                            </div>
                            <el-dialog
                                @close="finishEdit"
                                title="Edit Personal Information"
                                :visible.sync="userInfoDialogVisible"
                                width="400px"
                            >
                                <div class="edit-tip">Nickname</div>
                                <el-input v-model="userInfo.nickname" :disabled="notUserNicknameEdit" @change="saveUserNickname">
                                    <el-button slot="append" type="warning" icon="el-icon-edit" @click="notUserNicknameEdit = false"
                                        >Edit
                                    </el-button>
                                </el-input>
                                <div v-if="userPasswordEdit">
                                    <div class="edit-tip">Old Password</div>
                                    <el-input v-model="userPassword1" show-password></el-input>
                                    <div class="edit-tip">New Password</div>
                                    <el-input v-model="userPassword2" show-password></el-input>
                                    <div class="edit-tip">Confirm New Password</div>
                                    <el-input v-model="userPassword3" show-password></el-input>
                                    <div class="edit-tip"></div>
                                    <el-button @click="savePassword" plain>Confirm Modification</el-button>
                                </div>
                                <div v-else>
                                    <div class="edit-tip">Password</div>
                                    <el-input value="123456" :disabled="true" show-password>
                                        <el-button slot="append" type="warning" icon="el-icon-edit" @click="userPasswordEdit = true"
                                            >Edit
                                        </el-button>
                                    </el-input>
                                </div>
                                <span slot="footer" class="dialog-footer">
                                    <el-button @click="userInfoDialogVisible = false">Done</el-button>
                                </span>
                            </el-dialog>
                        </div>
                    </div>
                    <div class="user-info-space">
                        <el-button type="primary" plain @click="eidtAddress = true">Edit Address</el-button>
                    </div>
                </div>
                <div class="idle-container">
                    <el-tabs v-model="activeName" @tab-click="handleClick">
                        <el-tab-pane label="Recently Published" name="1"></el-tab-pane>
                        <el-tab-pane label="Unlisted" name="2"></el-tab-pane>
                        <el-tab-pane label="My Shopping Cart" name="3"></el-tab-pane>
                        <el-tab-pane label="Sales Record" name="4"></el-tab-pane>
                        <el-tab-pane label="Purchase Record" name="5"></el-tab-pane>
                    </el-tabs>
                    <div class="idle-container-list">
                        <div v-for="(item, index) in dataList[activeName - 1]" :key="index" class="idle-container-list-item">
                            <div class="idle-container-list-item-detail" @click="toDetails(activeName, item)">
                                <el-image style="width: 100px; height: 100px" :src="item.imgUrl" fit="cover">
                                    <div slot="error" class="image-slot">
                                        <i class="el-icon-picture-outline">No Image</i>
                                    </div>
                                </el-image>
                                <div class="idle-container-list-item-text">
                                    <div class="idle-container-list-title">
                                        {{ item.idleName }}
                                    </div>
                                    <div class="idle-container-list-idle-details" v-html="item.idleDetails"></div>
                                    <div class="idle-container-list-idle-time">{{ item.timeStr }}</div>
                                    <div class="idle-item-foot">
                                        <div class="idle-price">
                                            ${{ item.idlePrice }}
                                            {{ activeName === '4' || activeName === '5' ? orderStatus[item.orderStatus] : '' }}
                                        </div>
                                        <el-button
                                            v-if="activeName !== '4' && activeName !== '5'"
                                            type="danger"
                                            size="mini"
                                            slot="reference"
                                            plain
                                            @click.stop="handle(activeName, item, index)"
                                            >{{ handleName[activeName - 1] }}
                                        </el-button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div v-show="eidtAddress" class="address-container">
                <el-page-header
                    class="address-container-back"
                    @back="eidtAddress = false"
                    content="Manage Address"
                    title="Back"
                ></el-page-header>
                <div class="address-container-add">
                    <div class="address-container-add-title">Add New Address</div>
                    <div class="address-container-add-item">
                        <el-input
                            placeholder="Please enter recipient name"
                            v-model="addressInfo.consigneeName"
                            maxlength="10"
                            show-word-limit
                        >
                            <div slot="prepend">Your Name</div>
                        </el-input>
                    </div>
                    <div class="address-container-add-item">
                        <el-input
                            placeholder="Please enter your phone number"
                            v-model="addressInfo.consigneePhone"
                            onkeyup="this.value=this.value.replace(/[^\d.]/g,'');"
                            maxlength="11"
                            show-word-limit
                        >
                            <div slot="prepend">Phone Number</div>
                        </el-input>
                    </div>
                    <div class="address-container-add-item">
                        <!-- <span class="demonstration">Building Number/Floor</span>
                        <el-cascader
                            :options="options"
                            placeholder="Please select"
                            v-model="selectedOptions"
                            @change="handleAddressChange"
                            :separator="' '"
                        >
                        </el-cascader> -->
                        <el-input-group>
                            <el-input placeholder="Building Number/Floor" disabled slot="prepend"></el-input>
                            <el-cascader
                                :options="options"
                                placeholder="Please select"
                                v-model="selectedOptions"
                                @change="handleAddressChange"
                                :separator="' '"
                                style="flex: 1"
                            />
                        </el-input-group>
                    </div>
                    <div class="address-container-add-item">
                        <el-input
                            placeholder="Please enter detailed address"
                            v-model="addressInfo.detailAddress"
                            maxlength="50"
                            show-word-limit
                        >
                            <div slot="prepend">Detailed Address</div>
                        </el-input>
                    </div>
                    <el-checkbox v-model="addressInfo.defaultFlag">Default Address</el-checkbox>
                    <el-button style="margin-left: 20px" @click="saveAddress">Save</el-button>
                </div>
                <div class="address-container-list">
                    <div style="color: #409eff; font-size: 15px; padding-left: 10px">Addresses</div>
                    <el-table stripe :data="addressData" style="width: 100%">
                        <el-table-column prop="consigneeName" label="Name" width="100"> </el-table-column>
                        <el-table-column prop="consigneePhone" label="Phone" width="120"> </el-table-column>
                        <el-table-column prop="detailAddressText" label="Address" width="270"> </el-table-column>
                        <el-table-column label="Action">
                            <template slot-scope="scope">
                                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit </el-button>
                                <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete </el-button>
                            </template>
                        </el-table-column>
                        <el-table-column label="Is Default" width="110">
                            <template slot-scope="scope">
                                <el-button v-if="!scope.row.defaultFlag" size="mini" @click="handleSetDefault(scope.$index, scope.row)"
                                    >Set Default
                                </el-button>
                                <div v-else style="padding-left: 10px; color: #409eff">{{ scope.row.defaultAddress }}</div>
                            </template>
                        </el-table-column>
                    </el-table>
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
import options from '../common/country-data.js';

export default {
    name: 'me',
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            imgFileList: [],
            addressInfo: {
                consigneeName: '',
                consigneePhone: '',
                provinceName: '',
                cityName: '',
                regionName: '',
                detailAddress: '',
                defaultFlag: false
            },
            activeName: '1',
            handleName: ['Unlist', 'Delete', 'Remove from Cart', '', ''],
            dataList: [[], [], [], [], []],
            orderStatus: ['Pending', 'On Hold', 'Completed', 'Canceled'],
            userInfoDialogVisible: false,
            notUserNicknameEdit: true,
            userPasswordEdit: false,
            userPassword1: '',
            userPassword2: '',
            userPassword3: '',
            eidtAddress: false,
            selectedOptions: [], // Store default values
            options: options, // Store city data
            userInfo: {
                email: '',
                avatar: '',
                nickname: '',
                signInTime: ''
            },
            addressData: []
        };
    },
    created() {
        if (!this.$globalData.userInfo.nickname) {
            this.$api.getUserInfo().then((res) => {
                if (res.status_code === 1) {
                    res.data.signInTime = res.data.signInTime.substring(0, 10);
                    this.$globalData.userInfo = res.data;
                    this.userInfo = this.$globalData.userInfo;
                }
            });
        } else {
            this.userInfo = this.$globalData.userInfo;
        }
        this.getAddressData();
        this.getIdleItemData();
        this.getMyOrder();
        this.getMySoldIdle();
        this.getMyFavorite();
    },
    methods: {
        getMyFavorite() {
            this.$api.getMyFavorite().then((res) => {
                if (res.status_code === 1) {
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.dataList[2].push({
                            favoriteId: res.data[i].id,
                            id: res.data[i].idleItem.id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + ' ' + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].idleItem.idlePrice
                        });
                    }
                }
            });
        },
        getMySoldIdle() {
            this.$api.getMySoldIdle().then((res) => {
                if (res.status_code === 1) {
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.dataList[3].push({
                            id: res.data[i].id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + ' ' + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].orderPrice,
                            orderStatus: res.data[i].orderStatus
                        });
                    }
                }
            });
        },
        getMyOrder() {
            this.$api.getMyOrder().then((res) => {
                if (res.status_code === 1) {
                    for (let i = 0; i < res.data.length; i++) {
                        let pictureList = JSON.parse(res.data[i].idleItem.pictureList);
                        this.dataList[4].push({
                            id: res.data[i].id,
                            imgUrl: pictureList.length > 0 ? pictureList[0] : '',
                            idleName: res.data[i].idleItem.idleName,
                            idleDetails: res.data[i].idleItem.idleDetails,
                            timeStr: res.data[i].createTime.substring(0, 10) + ' ' + res.data[i].createTime.substring(11, 19),
                            idlePrice: res.data[i].orderPrice,
                            orderStatus: res.data[i].orderStatus
                        });
                    }
                }
            });
        },
        getIdleItemData() {
            this.$api.getAllIdleItem().then((res) => {
                if (res.status_code === 1) {
                    for (let i = 0; i < res.data.length; i++) {
                        res.data[i].timeStr = res.data[i].releaseTime.substring(0, 10) + ' ' + res.data[i].releaseTime.substring(11, 19);
                        let pictureList = JSON.parse(res.data[i].pictureList);
                        res.data[i].imgUrl = pictureList.length > 0 ? pictureList[0] : '';
                        if (res.data[i].idleStatus === 1) {
                            this.dataList[0].push(res.data[i]);
                        } else if (res.data[i].idleStatus === 2) {
                            this.dataList[1].push(res.data[i]);
                        }
                    }
                }
            });
        },
        getAddressData() {
            this.$api.getAddress().then((res) => {
                if (res.status_code === 1) {
                    let data = res.data;
                    for (let i = 0; i < data.length; i++) {
                        data[i].detailAddressText = data[i].provinceName + data[i].cityName + data[i].regionName + data[i].detailAddress;
                        data[i].defaultAddress = data[i].defaultFlag ? 'Default' : 'Set as Default';
                    }
                    this.addressData = data;
                }
            });
        },
        handleClick(tab, event) {
            console.log(this.activeName);
        },
        saveUserNickname() {
            this.notUserNicknameEdit = true;
            this.$api
                .updateUserPublicInfo({
                    nickname: this.userInfo.nickname
                })
                .then((res) => {
                    this.$globalData.userInfo.nickname = this.userInfo.nickname;
                });
        },
        savePassword() {
            if (!this.userPassword1 || !this.userPassword2) {
                this.$message.error('Password is empty!');
            } else if (this.userPassword2 === this.userPassword3) {
                this.$api
                    .updatePassword({
                        oldPassword: this.userPassword1,
                        newPassword: this.userPassword2
                    })
                    .then((res) => {
                        if (res.status_code === 1) {
                            this.userPasswordEdit = false;
                            this.$message({
                                message: 'Modification successful!',
                                type: 'success'
                            });
                            this.userPassword1 = '';
                            this.userPassword2 = '';
                            this.userPassword3 = '';
                        } else {
                            this.$message.error('Old password incorrect, modification failed!');
                        }
                    });
            } else {
                this.$message.error('Passwords do not match!');
            }
        },
        finishEdit() {
            this.notUserNicknameEdit = true;
            this.userInfoDialogVisible = false;
            this.userPasswordEdit = false;
        },
        handleAddressChange(value) {
            this.addressInfo.provinceName = value[0];
            this.addressInfo.cityName = value[1];
            this.addressInfo.regionName = value[2];
        },
        handleEdit(index, row) {
            this.addressInfo = JSON.parse(JSON.stringify(row));
            this.selectedOptions = ['', '', ''];
            this.selectedOptions[0] = row.provinceName;
            this.selectedOptions[1] = row.cityName;
            this.selectedOptions[2] = row.regionName;
        },
        handleDelete(index, row) {
            this.$confirm('Are you sure to delete this address?', 'Prompt', {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            })
                .then(() => {
                    this.$api
                        .deleteAddress(row)
                        .then((res) => {
                            if (res.status_code === 1) {
                                this.$message({
                                    message: 'Deleted successfully!',
                                    type: 'success'
                                });
                                this.addressData.splice(index, 1);
                                if (row.defaultFlag && this.addressData.length > 0) {
                                    this.addressData[0].defaultFlag = true;
                                    this.addressData[0].defaultAddress = 'Default';
                                    this.update({
                                        id: this.addressData[0].id,
                                        defaultFlag: true
                                    });
                                }
                            } else {
                                this.$message.error('System error, failed to delete!');
                            }
                        })
                        .catch(() => {
                            this.$message.error('Network error!');
                        });
                })
                .catch(() => {});
        },
        handleSetDefault(index, row) {
            row.defaultFlag = true;
            this.update(row);
        },
        toDetails(activeName, item) {
            if (activeName === '4' || activeName === '5') {
                this.$router.push({ path: '/order', query: { id: item.id } });
            } else {
                this.$router.push({ path: '/details', query: { id: item.id } });
            }
        },
        handle(activeName, item, index) {
            this.$confirm('Are you sure?', 'Prompt', {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning'
            })
                .then(() => {
                    if (activeName === '1') {
                        this.$api
                            .updateIdleItem({
                                id: item.id,
                                idleStatus: 2
                            })
                            .then((res) => {
                                if (res.status_code === 1) {
                                    this.dataList[0].splice(index, 1);
                                    item.idleStatus = 2;
                                    this.dataList[1].unshift(item);
                                } else {
                                    this.$message.error(res.msg);
                                }
                            });
                    } else if (activeName === '2') {
                        this.$api
                            .updateIdleItem({
                                id: item.id,
                                idleStatus: 0
                            })
                            .then((res) => {
                                if (res.status_code === 1) {
                                    this.dataList[1].splice(index, 1);
                                } else {
                                    this.$message.error(res.msg);
                                }
                            });
                    } else if (activeName === '3') {
                        this.$api
                            .deleteFavorite({
                                id: item.favoriteId
                            })
                            .then((res) => {
                                if (res.status_code === 1) {
                                    this.$message({
                                        message: 'Removed from cart!',
                                        type: 'success'
                                    });
                                    this.dataList[2].splice(index, 1);
                                } else {
                                    this.$message.error(res.msg);
                                }
                            });
                    }
                })
                .catch(() => {});
        },
        fileHandleSuccess(response, file, fileList) {
            let imgUrl = response.data;
            this.imgFileList = [];
            this.$api
                .updateUserPublicInfo({
                    avatar: imgUrl
                })
                .then((res) => {
                    this.userInfo.avatar = imgUrl;
                    this.$globalData.userInfo.avatar = imgUrl;
                });
        },
        update(data) {
            this.$api
                .updateAddress(data)
                .then((res) => {
                    if (res.status_code === 1) {
                        this.getAddressData();
                        this.$message({
                            message: 'Modification successful!',
                            type: 'success'
                        });
                    } else {
                        this.$message.error('System error, modification failed!');
                    }
                })
                .catch(() => {
                    this.$message.error('Network error!');
                });
        },
        saveAddress() {
            if (this.addressInfo.id) {
                this.update(this.addressInfo);
                this.addressInfo = {
                    consigneeName: '',
                    consigneePhone: '',
                    provinceName: '',
                    cityName: '',
                    regionName: '',
                    detailAddress: '',
                    defaultFlag: false
                };
                this.selectedOptions = [];
            } else {
                if (this.addressData.length >= 5) {
                    this.$message.error('Reached maximum address limit!');
                } else {
                    this.$api
                        .addAddress(this.addressInfo)
                        .then((res) => {
                            if (res.status_code === 1) {
                                this.getAddressData();
                                this.$message({
                                    message: 'Added successfully!',
                                    type: 'success'
                                });
                                this.selectedOptions = [];
                                this.addressInfo = {
                                    consigneeName: '',
                                    consigneePhone: '',
                                    provinceName: '',
                                    cityName: '',
                                    regionName: '',
                                    detailAddress: '',
                                    defaultFlag: false
                                };
                            } else {
                                this.$message.error('System error, failed to add!');
                            }
                        })
                        .catch(() => {
                            this.$message.error('Network error!');
                        });
                }
            }
        }
    }
};
</script>

<style scoped>
.user-info-container {
    width: 100%;
    height: 200px;
    border-bottom: 15px solid #f6f6f6;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.user-info-details {
    display: flex;
    height: 140px;
    align-items: center;
    margin: 20px 40px;
}

.user-info-details-text {
    margin-left: 20px;
}

.user-info-details-text-nickname {
    font-size: 26px;
    font-weight: 600;
    margin: 10px 0;
}

.user-info-details-text-time {
    font-size: 14px;
    margin-bottom: 10px;
}

.user-info-space {
    margin-right: 90px;
}

.idle-container {
    padding: 0 20px;
}

.idle-container-list {
    min-height: 55vh;
}

.idle-container-list-item {
    border-bottom: 1px solid #eeeeee;
    cursor: pointer;
}

.idle-container-list-item:last-child {
    border-bottom: none;
}

.idle-container-list-item-detail {
    height: 120px;
    display: flex;
    align-items: center;
}

.idle-container-list-item-text {
    margin-left: 10px;
    height: 100px;
    max-width: 800px;
}

.idle-container-list-title {
    font-weight: 600;
    font-size: 18px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.idle-container-list-idle-details {
    font-size: 14px;
    color: #555555;
    padding-top: 5px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.idle-container-list-idle-time {
    font-size: 13px;
    padding-top: 5px;
}

.idle-price {
    font-size: 15px;
    padding-top: 5px;
    color: red;
}

.edit-tip {
    font-size: 14px;
    margin: 10px 5px;
}

.address-container {
    padding: 10px 20px;
}

.address-container-back {
    margin-bottom: 10px;
}

.address-container-add-title {
    font-size: 15px;
    color: #409eff;
    padding: 10px;
}

.address-container-add-item {
    margin-bottom: 20px;
}

.demonstration {
    color: #666666;
    font-size: 14px;
    padding: 10px;
}

.address-container-add {
    padding: 0 200px;
}

.address-container-list {
    padding: 30px 100px;
}

.idle-item-foot {
    width: 800px;
    display: flex;
    justify-content: space-between;
}
</style>
