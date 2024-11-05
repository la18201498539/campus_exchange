<template>
    <div class="login-container">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title">Admin Management</div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="Please enter admin account" v-model="userForm.accountNumber" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input
                        placeholder="Please enter admin password"
                        v-model="userForm.adminPassword"
                        class="login-input"
                        @keyup.enter.native="login"
                        show-password
                    >
                        <template slot="prepend">
                            <div class="el-icon-lock"></div>
                        </template>
                    </el-input>
                    <div class="login-submit">
                        <el-button type="primary" @click="login">Login</el-button>
                    </div>
                    <div class="other-submit">
                        <router-link to="/login" class="sign-in-text">Student Login</router-link>
                    </div>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'login',
    data() {
        return {
            userForm: {
                accountNumber: '',
                adminPassword: ''
            }
        };
    },
    methods: {
        login() {
            // verify login email
            if (!this.userForm.accountNumber.endsWith('@bu.edu')) {
                this.$message.error('Please use your BU email');
                return;
            }
            this.$api
                .adminLogin({
                    accountNumber: this.userForm.accountNumber,
                    adminPassword: this.userForm.adminPassword
                })
                .then((res) => {
                    console.log(res);
                    if (res.status_code === 1) {
                        console.log(res);
                        this.$sta.isLogin = true;
                        this.$sta.adminName = res.data.adminName;
                        this.$router.replace({ path: '/platform-admin' });
                    } else {
                        this.$message.error('Login failed, incorrect account or password!');
                    }
                });
        }
    }
};
</script>

<style scoped>
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100%;
    background-color: #f1f1f1;
}
.login-body {
    padding: 30px;
    width: 400px;
    height: 100%;
}
.login-title {
    padding-bottom: 30px;
    text-align: center;
    font-weight: 600;
    font-size: 20px;
    color: #409eff;
    cursor: pointer;
}
.login-input {
    margin-bottom: 20px;
}
.login-submit {
    display: flex;
    justify-content: center;
}
.sign-in-text {
    color: #409eff;
    font-size: 16px;
    text-decoration: none;
    line-height: 28px;
}
.other-submit {
    display: flex;
    justify-content: space-between;
    margin-top: 10px;
}
</style>
