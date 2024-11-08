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
                <el-input placeholder="Please enter BU email..." v-model="userInfo.email" class="sign-in-input" clearable>
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

          <!-- Verification Code Dialog -->
          <el-dialog title="Verify Email" :visible.sync="showVerifyDialog" width="30%">
            <span>Enter the 6-digit code sent to your email:</span>
            <el-input v-model="activeCode" placeholder="6-digit code" maxlength="6" class="sign-in-input"/>
            <div slot="footer" class="dialog-footer">
              <el-button @click="verifyCode">Verify</el-button>
            </div>
          </el-dialog>
        </el-card>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: 'sign-in',
    data() {
        return {
            userPassword2: '',
            userInfo: {
                email: '',
                userPassword: '',
                nickname: ''
            },
          showVerifyDialog: false,
          activeCode: '',
        };
    },
    methods: {
        toLogin() {
            this.$router.replace({ path: '/login' });
        },
        signIn() {
            console.log(this.userInfo.nickname);

            // verify user email with @bu.edu
            const emailRegex = /^[a-zA-Z0-9._-]+@bu\.edu$/;

            if (this.userInfo.email && this.userInfo.userPassword && this.userInfo.nickname) {
                // verify email
                if (!emailRegex.test(this.userInfo.email)) {
                    this.$message.error('Please enter a valid BU email address ending with "@bu.edu"!');
                    return;
                }

                // verify password
                if (this.userInfo.userPassword !== this.userPassword2) {
                    this.$message.error('The passwords entered do not match!');
                } else {
                    this.$api
                        .signIn(this.userInfo)
                        .then((res) => {
                            if (res.status_code === 1) {
                              this.$confirm(
                                  `A verification email has been sent to ${this.userInfo.email}. Please check your inbox for the 6-digit verification code to activate your email.`,
                                  'Email Sent',
                                  {
                                    confirmButtonText: 'OK',
                                    type: 'info',
                                    // callback: () => {
                                    //   // Redirect to login page after confirmation
                                    //   this.$router.replace({ path: '/login' });
                                    // }
                                  }
                              );

                              //显示输入验证码框
                              this.showVerifyDialog = true;
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
        },

      //然后请求后端验证
      verifyCode() {
        axios.post('http://47.252.36.46:8080/user/verifyCode', {
          email: this.userInfo.email,
          activeCode: this.activeCode
        }).then(res => {
          if (res.data.status_code === 1) { // 验证成功
            this.$message.success("Registration successful! Account activated.");
            this.$router.push('/login');
          } else { // 验证失败
            this.$message.error("Invalid verification code. Please try again.");
            this.showVerifyDialog = true; // 保持弹窗显示状态
          }
        }).catch(error => {
          this.$message.error("An error occurred. Please try again later.");
        });
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
