<template>
    <div>
        <app-head></app-head>
        <app-body>
            <div class="release-idle-container">
                <div class="release-idle-container-title">Post Item</div>
                <div class="release-idle-container-form">
                    <el-input placeholder="Please enter item title" v-model="idleItemInfo.idleName" maxlength="30" show-word-limit>
                    </el-input>
                    <el-input
                        class="release-idle-details-text"
                        type="textarea"
                        autosize
                        placeholder="Please enter a detailed description of the item..."
                        v-model="idleItemInfo.idleDetails"
                        maxlength="1000"
                        show-word-limit
                    >
                    </el-input>
                    <div class="release-idle-place">
                        <div class="release-tip">Your Location</div>
                        <el-cascader
                            :options="options"
                            placeholder="Please select"
                            v-model="selectedOptions"
                            @change="handleChange"
                            :separator="' '"
                            style="width: 90%"
                        >
                        </el-cascader>
                    </div>
                    <div style="display: flex; justify-content: space-between">
                        <div>
                            <div class="release-tip">Item Category</div>
                            <el-select v-model="idleItemInfo.idleLabel" placeholder="Please Select Item Category">
                                <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                            </el-select>
                        </div>
                        <div v-show="idleItemInfo.idleLabel !== 5" style="width: 300px">
                            <el-input-number v-model="idleItemInfo.idlePrice" :precision="2" :step="10" :max="10000000">
                                <div slot="prepend">Price</div>
                            </el-input-number>
                        </div>
                    </div>
                    <div class="release-idle-container-picture">
                        <div class="release-idle-container-picture-title">Upload Item Photos</div>
                        <el-upload
                            action="http://47.252.36.46:8080/file"
                            :on-preview="fileHandlePreview"
                            :on-remove="fileHandleRemove"
                            :on-success="fileHandleSuccess"
                            :show-file-list="showFileList"
                            :limit="10"
                            :on-exceed="handleExceed"
                            accept="image/*"
                            drag
                            multiple
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">Drag images here, or <em>click to upload</em></div>
                        </el-upload>
                        <div class="picture-list">
                            <el-image
                                style="width: 600px; margin-bottom: 2px"
                                fit="contain"
                                v-for="(img, index) in imgList"
                                :key="index"
                                :src="img"
                                :preview-src-list="imgList"
                            ></el-image>
                        </div>
                        <el-dialog :visible.sync="imgDialogVisible">
                            <img width="100%" :src="dialogImageUrl" alt="" />
                        </el-dialog>
                    </div>
                    <div style="display: flex; justify-content: center; margin-top: 30px; margin-bottom: 30px">
                        <el-button type="primary" plain @click="releaseButton">Confirm</el-button>
                    </div>
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
    name: 'release',
    components: {
        AppHead,
        AppBody,
        AppFoot
    },
    data() {
        return {
            imgDialogVisible: false,
            dialogImageUrl: '',
            showFileList: true,
            options: options,
            selectedOptions: [],
            options2: [
                {
                    value: 1,
                    label: 'Digital Technology'
                },
                {
                    value: 2,
                    label: 'Daily Supplies'
                },
                {
                    value: 3,
                    label: 'Sports Related'
                },
                {
                    value: 4,
                    label: 'Books and Notes'
                },
                {
                    value: 5,
                    label: 'Announcement Display'
                }
            ],
            imgList: [],
            idleItemInfo: {
                idleName: '',
                idleDetails: '',
                pictureList: '',
                idlePrice: 0,
                idlePlace: '',
                idleLabel: ''
            }
        };
    },
    methods: {
        handleChange(value) {
            console.log(value);
            this.idleItemInfo.idlePlace = value[1];
        },
        fileHandleRemove(file, fileList) {
            console.log(file, fileList);
            for (let i = 0; i < this.imgList.length; i++) {
                if (this.imgList[i] === file.response.data) {
                    this.imgList.splice(i, 1);
                }
            }
        },
        fileHandlePreview(file) {
            console.log(file);
            this.dialogImageUrl = file.response.data;
            this.imgDialogVisible = true;
        },
        fileHandleSuccess(response, file, fileList) {
            console.log('file:', response, file, fileList);
            this.imgList.push(response.data);
        },
        releaseButton() {
            this.idleItemInfo.pictureList = JSON.stringify(this.imgList);
            console.log(this.idleItemInfo);
            if (
                this.idleItemInfo.idleName &&
                this.idleItemInfo.idleDetails &&
                this.idleItemInfo.idlePlace &&
                this.idleItemInfo.idleLabel &&
                (this.idleItemInfo.idlePrice || this.idleItemInfo.idleLabel === 5)
            ) {
                this.$api
                    .addIdleItem(this.idleItemInfo)
                    .then((res) => {
                        if (res.status_code === 1) {
                            this.$message({
                                message: 'Post successful！',
                                type: 'success'
                            });
                            console.log(res.data);
                            this.$router.replace({ path: '/details', query: { id: res.data.id } });
                        } else {
                            this.$message.error('Post failed!' + res.msg);
                        }
                    })
                    .catch((e) => {
                        this.$message.error('Please fill in complete information');
                    });
            } else {
                this.$message.error('Please fill in complete information！');
            }
        },
        handleExceed(files, fileList) {
            this.$message.warning(
                `Limit 10 pictures, selected ${files.length} pictures this time, total ${files.length + fileList.length} pictures selected`
            );
        },
        isNotice() {}
    }
};
</script>

<style scoped>
.release-idle-container {
    min-height: 85vh;
}

.release-idle-container-title {
    font-size: 18px;
    padding: 30px 0;
    font-weight: 600;
    width: 100%;
    text-align: center;
}

.release-idle-container-form {
    padding: 0 180px;
}

.release-idle-details-text {
    margin: 20px 0;
}
.release-idle-place {
    margin-bottom: 15px;
}
.release-tip {
    color: #555555;
    float: left;
    padding-right: 5px;
    height: 36px;
    line-height: 36px;
    font-size: 14px;
}
.release-idle-container-picture {
    margin: 20px 0;
}
.release-idle-container-picture-title {
    margin: 10px 0;
    color: #555555;
    font-size: 14px;
}
.picture-list {
    margin: 20px 0;
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
