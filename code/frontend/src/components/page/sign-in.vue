<template>
    <div class="sign-in-container">
        <el-card class="box-card">
            <div class="sign-in-body">
                <div class="sign-in-title">Sign Up</div>
                <el-input
                    placeholder="Please enter a nickname..."
                    maxlength="30"
                    v-model="userInfo.nickname"
                    class="sign-in-input"
                    clearable
                >
                    <template slot="prepend">
                        <div class="el-icon-user-solid"></div>
                    </template>
                </el-input>
                <el-input placeholder="Please enter BU email..." v-model="userInfo.accountNumber" class="sign-in-input" clearable>
                    <template slot="prepend">
                        <div class="el-icon-message"></div>
                    </template>
                </el-input>
                <el-input
                    placeholder="Please enter a password..."
                    show-password
                    maxlength="16"
                    v-model="userInfo.userPassword"
                    class="sign-in-input"
                    clearable
                >
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <el-input
                    placeholder="Please re-enter the password..."
                    show-password
                    maxlength="16"
                    v-model="userPassword2"
                    @keyup.enter.native="signIn"
                    class="sign-in-input"
                    clearable
                >
                    <template slot="prepend">
                        <div class="el-icon-lock"></div>
                    </template>
                </el-input>
                <div class="sign-in-submit">
                    <el-button type="primary" @click="signIn">Submit</el-button>
                </div>
                <div class="login-container">
                    <span @click="toLogin" class="login-text">Login</span>
                </div>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: 'sign-in',
    data() {
        return {
            userPassword2: '',
            userInfo: {
                accountNumber: '',
                userPassword: '',
                nickname: ''
            }
        };
    },
    methods: {
        toLogin() {
            this.$router.replace({ path: '/login' });
        },
        signIn() {
            console.log(this.userInfo.nickname);
            if (this.userInfo.accountNumber && this.userInfo.userPassword && this.userInfo.nickname) {
                if (this.userInfo.userPassword !== this.userPassword2) {
                    this.$message.error('The passwords entered do not match!');
                } else {
                    this.$api
                        .signIn(this.userInfo)
                        .then((res) => {
                            if (res.status_code === 1) {
                                this.$message({
                                    message: 'Registration successful!',
                                    type: 'success'
                                });
                                this.$router.replace({ path: '/login' });
                            } else {
                                this.$message.error('Registration failed, user already exists!');
                            }
                        })
                        .catch((e) => {
                            console.log(e);
                            this.$message.error('Registration failed, network error!');
                        });
                }
            } else {
                this.$message.error('Registration information is incomplete!');
            }
        }
    }
};
</script>

<style scoped>
.sign-in-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100%;
    background-color: #f1f1f1;
}

.sign-in-body {
    padding: 30px;
    width: 400px;
    height: 100%;
}

.sign-in-title {
    padding-bottom: 30px;
    text-align: center;
    font-weight: 600;
    font-size: 20px;
    color: #409eff;
}

.sign-in-input {
    margin-bottom: 20px;
}
.sign-in-submit {
    display: flex;
    justify-content: center;
}
.login-container {
    padding: 0 10px;
}
.login-text {
    color: #409eff;
    font-size: 16px;
    cursor: pointer;
}
</style>
