<template>
    <div class="login-container">
        <el-card class="box-card">
            <div class="login-body">
                <div class="login-title" @click="toIndex">Campus Exchange</div>
                <el-form ref="form" :model="userForm">
                    <el-input placeholder="Please enter your BU email..." v-model="userForm.email" class="login-input">
                        <template slot="prepend">
                            <div class="el-icon-user-solid"></div>
                        </template>
                    </el-input>
                    <el-input
                        placeholder="Please enter your password..."
                        v-model="userForm.userPassword"
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
                        <router-link to="/register" class="sign-in-text">Sign Up</router-link>
                        <router-link to="/login-admin" class="sign-in-text">Admin Login</router-link>
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
                email: '',
                userPassword: ''
            }
        };
    },

    methods: {
        login() {
            // verify login email
            if (!this.userForm.email.endsWith('@bu.edu')) {
                this.$message.error('Please use your BU email');
                return;
            }

            this.$api
                .userLogin({
                    email: this.userForm.email,
                    userPassword: this.userForm.userPassword
                })
                .then((res) => {
                    console.log(res);
                    if (res.status_code === 1) {
                        res.data.signInTime = res.data.signInTime.substring(0, 10);
                        this.$globalData.userInfo = res.data;
                        this.$router.replace({ path: '/index' });
                    } else {
                        this.$message.error(res.msg);
                    }
                });
        },
        toIndex() {
            this.$router.replace({ path: '/index' });
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

.sign-in-container {
    padding: 0 10px;
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
