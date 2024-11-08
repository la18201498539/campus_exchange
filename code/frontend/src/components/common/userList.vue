<template>
    <div class="main-border">
        <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
            <span v-show="this.mode != 3" class="app-title">
                <el-input placeholder="User account..." v-model="searchValue" @keyup.enter.native="searchIdle">
                    <el-button slot="append" icon="el-icon-search" @click="searchIdle"></el-button>
                </el-input>
            </span>
            <el-menu-item index="1">Active User</el-menu-item>
            <el-menu-item index="2">Illegal User</el-menu-item>
            <el-menu-item index="3">Admin</el-menu-item>
            <div v-show="this.mode == 3" class="addAdminButton">
                <el-button size="mini" type="success" @click="adminRegVisible = true">Add Admin</el-button>
                <el-dialog title="Add Admin" :visible.sync="adminRegVisible" width="25%">
                    <span style="margin-left: 10px">Full Name</span>
                    <el-input
                        v-model="adminName"
                        maxlength="8"
                        placeholder="Enter name"
                        style="padding: 10px 0"
                        clearable
                        required
                    ></el-input>
                    <span style="margin-left: 10px">Username</span>
                    <el-input
                        v-model="adminAccount"
                        minlength="8"
                        maxlength="10"
                        placeholder="Enter username"
                        style="padding: 10px 0"
                        clearable
                        required
                    ></el-input>
                    <span style="margin-left: 10px">Password</span>
                    <el-input
                        v-model="adminPassword"
                        minlength="8"
                        placeholder="Enter password"
                        style="padding: 10px 0"
                        show-password
                        required
                    ></el-input>
                    <span style="margin-left: 10px">Confirm Password</span>
                    <el-input
                        v-model="adminRePassword"
                        minlength="10"
                        placeholder="Enter password again"
                        style="padding: 10px 0"
                        show-password
                        required
                    ></el-input>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="regAdmin">Add</el-button>
                    </span>
                </el-dialog>
            </div>
        </el-menu>
        <el-table v-show="this.mode == 1" :data="userData" stripe style="width: 100%; color: #5a5c61">
            <el-table-column label="avatar" width="50">
                <template slot-scope="scope">
                    <el-avatar shape="square" :size="23" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="email" label="email" show-overflow-tooltip min-width="150" width="150"> </el-table-column>
            <el-table-column prop="nickname" label="nickname" show-overflow-tooltip min-width="150" width="150"> </el-table-column>
            <el-table-column prop="signInTime" label="signInTime" show-overflow-tooltip width="200"> </el-table-column>
            <!-- <el-table-column label="operation">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" @click="sealUser(scope.$index)">Suspend</el-button>
                </template>
            </el-table-column> -->
        </el-table>
        <!-- <el-table v-show="this.mode == 2" :data="badUserData" stripe style="width: 100%; color: #5a5c61">
            <el-table-column label="avatar" width="50">
                <template slot-scope="scope">
                    <el-avatar shape="square" :size="23" :src="scope.row.avatar"></el-avatar>
                </template>
            </el-table-column>
            <el-table-column prop="email" label="email" show-overflow-tooltip min-width="150" width="150"> </el-table-column>
            <el-table-column prop="nickname" label="nickname" show-overflow-tooltip width="150"> </el-table-column>
            <el-table-column prop="signInTime" label="signInTime" show-overflow-tooltip width="200"> </el-table-column>
            <el-table-column label="operation">
                <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="unsealUser(scope.$index)">Unsuspend</el-button>
                </template>
            </el-table-column>
        </el-table> -->
        <el-table v-show="this.mode == 3" :data="userManage" stripe style="width: 100%; color: #5a5c61">
            <el-table-column prop="email" label="email" show-overflow-tooltip width="200"> </el-table-column>
            <el-table-column prop="adminName" label="adminName"> </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page.sync="nowPage"
                :page-size="7"
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
    name: 'userList',
    created() {
        this.getUserData();
    },
    methods: {
        handleCurrentChange(val) {
            this.nowPage = val;
            if (this.mode == 1) {
                this.getUserData();
            }
            if (this.mode == 2) {
                this.getBadUserData();
            }
            if (this.mode == 3) {
                this.getUserManage();
            }
        },
        handleSelect(val) {
            if (this.mode !== val) {
                this.mode = val;
                if (val == 1) {
                    this.nowPage = 1;
                    this.getUserData();
                }
                if (val == 2) {
                    this.nowPage = 1;
                    this.getBadUserData();
                }
                if (val == 3) {
                    this.nowPage = 1;
                    this.getUserManage();
                }
            }
        },
        getUserData() {
            //Active User
            this.$api
                .getUserData({
                    page: this.nowPage,
                    nums: 8,
                    status: 0
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.userData = res.data.list;
                        this.total = res.data.count;
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        getBadUserData() {
            //Illegal User
            this.$api
                .getUserData({
                    page: this.nowPage,
                    nums: 8,
                    status: 1
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.badUserData = res.data.list;
                        this.total = res.data.count;
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        getUserManage() {
            // Administrator
            this.$api
                .getUserManage({
                    page: this.nowPage,
                    nums: 8
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.userManage = res.data.list;
                        this.total = res.data.count;
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        sealUser(i) {
            console.log(this.userData[i].id);
            this.$api
                .updateUserStatus({
                    id: this.userData[i].id,
                    status: 1
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.getUserData();
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        unsealUser(i) {
            this.$api
                .updateUserStatus({
                    id: this.badUserData[i].id,
                    status: 0
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        this.getBadUserData();
                    } else {
                        this.$message.error(res.msg);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        regAdmin() {
            if (this.adminPassword == this.adminRePassword) {
                this.$api
                    .regAdministrator({
                        adminName: this.adminName,
                        email: this.adminAccount,
                        adminPassword: this.adminPassword
                    })
                    .then((res) => {
                        if (res.status_code == 1) {
                            this.total = this.total + 1;
                            this.nowPage = Math.ceil(this.total / 8);
                            console.log(this.nowPage);
                            this.getUserManage();
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                    .catch((e) => {
                        console.log(e);
                        this.$message.error('Failed to add, duplicate account or network error.');
                    });
                this.adminRegVisible = false;
            } else {
                this.$message.error('The passwords entered do not match!');
            }
        },
        searchIdle() {
            this.$api
                .queryUser({
                    searchValue: this.searchValue,
                    mode: this.mode,
                    page: this.nowPage,
                    nums: 8
                })
                .then((res) => {
                    if (res.status_code == 1) {
                        if (this.mode == 1) {
                            this.userData = res.data.list;
                            this.total = res.data.count;
                        } else if (this.mode == 2) {
                            this.badUserData = res.data.list;
                            this.total = res.data.count;
                        } else {
                            this.userManage = res.data.list;
                            this.total = res.data.count;
                        }
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
            total: 63,
            adminRegVisible: false,
            adminAccount: '',
            adminPassword: '',
            adminRePassword: '',
            adminName: '',
            userData: [],
            badUserData: [],
            userManage: [],
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
.addAdminButton {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    height: 60px;
    outline: none;
}
</style>
