<template>
    <el-dialog :visible.sync="visible"
               :close-on-click-modal="false"
               @close="close"
               width="1000px"
               title="消息"
               id="chat-dialog">
        <div class="flex-row" style="height: 600px;">
            <div style="width: 200px!important;background-color: #fff">
                <div style="height: 100px">
                    <!--<img class="avatar" width="40" height="40" :alt="user.name" :src="user.img">-->
                    <p class="name">{{dicMap.name}}</p>
                    <el-input size="small" style="width: 98%" v-model="name" placeholder="搜索用户"></el-input>
                </div>
                <div style=" height: 580px;">
                </div>
            </div>
            <div class=" flex-column chatMain">
                <div class=" flex-column" style="background-color: #eee">
                    <div class="message-main">
                        <ul >
                            <li v-for="item in messages">
                                <p class="message-time">
                                    <span>{{ item.date | formatDate}}</span>
                                </p>
                                <div  :class="{ self: item.self }">
                                    <!--<img class="avatar" width="30" height="30"-->
                                    <!--:src="item.self ? user.img : session.user.img"/>-->
                                    <div class="avatar">张三</div>
                                    <div class="message-text">{{ item.content }}</div>
                                </div>
                            </li>
                        </ul>
                    </div>

                </div>
                <div class="chatText">
                    <el-input type="textarea" :rows="7" style="width: 100%;height: 155px"
                              placeholder="   按 Ctrl + Enter 发送" v-model="content"></el-input>
                </div>
            </div>
        </div>
    </el-dialog>
</template>
<script>

    export default {
        props: [],
        data() {
            return {
                visible: true,
                content: '',
                name: undefined,
                messages: [
                    {date: new Date(),
                    content:'hello'
                    },
                    {
                        date: new Date(),
                        content:'hello hi',
                        self: true
                    }
                ]
            }
        },
        computed: {
            dicMap() {
                return this.$store.state.__dicMap;
            }
        },
        methods: {
            close() {
                this.$emit('close');
            },
        },
        mounted() {

        }
    }
</script>

<style lang="less" scoped>
    .chatMain {
        width: 740px;
    }

    .chatText {
        height: 160px;
    }
    .message-time {
        margin: 7px 0;
        text-align: center;
        > span {
            display: inline-block;
            padding: 0 18px;
            font-size: 12px;
            border-radius: 2px;
            background-color: #dcdcdc;
        }
    }
    .message-main {
        padding: 10px 15px;
        overflow-y: auto;
        ul{
            padding-left: 3px;
        }
        li {
            list-style-type:none;
        }
        .message-time {
            margin: 7px 0;
            text-align: center;

            > span {
                display: inline-block;
                padding: 0 14px;
                font-size: 12px;
                border-radius: 2px;
                background-color: #dcdcdc;
            }
        }
        .avatar {
            float: left;
            line-height: 2.5;
            margin-right: 8px;
        }
        .message-text {
            display: inline-block;
            position: relative;
            padding: 0 10px;
            max-width: ~'calc(100% - 40px)';
            min-height: 30px;
            line-height: 2.5;
            font-size: 14px;
            text-align: left;
            word-break: break-all;
            background-color: #b2e281;
            border-radius: 4px;
            &:before {
                content: " ";
                position: absolute;
                top: 9px;
                right: 100%;
                border: 6px solid transparent;
                border-right-color: #b2e281;
            }
        }

        .self {
            text-align: right;
            .avatar {
                float: right;
                line-height: 2.5;
                margin-left: 8px;
            }
            .message-text {
                background-color: #b2e281;
                &:before {
                    right: inherit;
                    left: 100%;
                    border-right-color: transparent;
                    border-left-color: #b2e281;
                }
            }
        }
    }
</style>