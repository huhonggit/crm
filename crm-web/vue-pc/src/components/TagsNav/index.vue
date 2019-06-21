<!--
在此处采用2个div 进行左右滚动 避免出现滚动条
-->
<template>
    <div class="tags-view" ref="tagsView">
        <div class="tags-scoll-view" ref="tagsScollView" :style="{left: tagBodyLeft + 'px'}">
            <router-link
                    v-for="tag in options"
                    ref="tag"
                    :key="tag.route"
                    :class="isActive(tag.route)?'active':''"
                    :to="{ path: tag.route, query: tag.query, fullPath: tag.fullPath }"
                    tag="span"
                    class="tags-view-item"
                    @contextmenu.prevent.native="openMenu(tag,$event)" >
                {{ tag.name }}
                <span  class="el-icon-close" @click.prevent.stop="tabRemove(tag.route)" />
            </router-link>
            <ul id="menu" v-show="menuVisible" :style="{left:left+'px',top:top+'px'}" class="contextmenu">
                <li @click="closeOthersTags">
                    关闭其他
                </li>
                <li @click="closeAllTags(selectedTag)">
                    关闭所有
                </li>
            </ul>
        </div>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                tagBodyLeft: 0,
                menuVisible: false,
                top: 0,
                left: 0,
                selectedTag:undefined
            }
        },
        watch: {
            $route() {
                this.tabAdd();
                this.moveToCurrentTab()
            },
            menuVisible(value) {
                if (value) {
                    document.body.addEventListener('click', this.closeMenu)
                } else {
                    document.body.removeEventListener('click', this.closeMenu)
                }
            }

        },
        computed: {
            options() {
                return this.$store.state.options;
            },
            activeIndex: {
                get() {
                    return this.$store.state.activeIndex;
                },
                set(val) {
                    this.$store.commit('set_active_index', val);
                }
            },
        },
        mounted:function(){

            setTimeout(() => {
                this.tabAdd()
            }, 50)
        },
        methods:{
            closeOthersTags() {
                let to = this.selectedTag
                this.$store.commit('delete_all',{ route: to.route, name: to.name})
                this.menuVisible = false
            },
            closeAllTags() {
                this.$store.commit('delete_all',null)
                this.menuVisible = false
                this.$router.push('/home');
            },
            handleScroll (offset) {
                const outerWidth = this.$refs.tagsView.offsetWidth
                const bodyWidth = this.$refs.tagsScollView.offsetWidth
                if (offset > 0) {
                    this.tagBodyLeft = Math.min(0, this.tagBodyLeft + offset)
                } else {
                    if (outerWidth < bodyWidth) {
                        if (this.tagBodyLeft < -(bodyWidth - outerWidth)) {
                            this.tagBodyLeft = this.tagBodyLeft
                        } else {
                            this.tagBodyLeft = Math.max(this.tagBodyLeft + offset, outerWidth - bodyWidth)
                        }
                    } else {
                        this.tagBodyLeft = 0
                    }
                }
            },
            tabAdd(){
                let to  = this.$route;
                this.$store.commit('add_tabs', { route: to.path, name: to.name});
            },
            moveToCurrentTab() {
                const tags = this.$refs.tag
                this.$nextTick(() => {
                    for (const tag of tags) {
                        if (tag.to.path === this.$route.path) {
                            this.moveToTarget(tag.$el)
                            break
                        }
                    }
                })
            },
            moveToTarget(tag) {
                //获取显示区域的div的大小
                const tagsView = this.$refs.tagsView;
                const tagsViewWidth = tagsView.offsetWidth
                const tagsScollView = this.$refs.tagsScollView;
                const tagsScollViewWidth = tagsScollView.offsetWidth
                if (tagsScollViewWidth < tagsViewWidth) {
                    this.tagBodyLeft = 0
                } else if (tag.offsetLeft < -this.tagBodyLeft) {
                    // 标签在可视区域左侧
                    this.tagBodyLeft = -tag.offsetLeft
                } else if (tag.offsetLeft > -this.tagBodyLeft && tag.offsetLeft + tag.offsetWidth < -this.tagBodyLeft + tagsViewWidth) {
                    // 标签在可视区域
                    this.tagBodyLeft = Math.min(0, outerWidth - tag.offsetWidth - tag.offsetLeft )
                } else {
                    // 标签在可视区域右侧
                    this.tagBodyLeft = -(tag.offsetLeft - (tagsViewWidth- tag.offsetWidth))
                }
            },
            isActive(route){
                return route === this.activeIndex?true:false;
            },
            tabRemove(targetName) {
                // 首页不可删除
                if (targetName == '/home') {
                    return;
                }
                this.$store.commit('delete_tabs', targetName);
                if (this.activeIndex === targetName) {
                    // 设置当前激活的路由
                    if (this.options && this.options.length >= 1) {
                        this.$store.commit('set_active_index', this.options[this.options.length - 1].route);
                        this.$router.push({ path: this.activeIndex });
                    } else {
                        this.$router.push({ path: '/' });
                    }
                }
            },
            openMenu(tag, e) {
                const menuMinWidth = 105
                // const offsetLeft = this.$el.getBoundingClientRect().left // container margin left
                const offsetWidth = this.$el.offsetWidth // container width
                const maxLeft = offsetWidth - menuMinWidth // left boundary
                const left = e.clientX // 15: margin right

                if (left > maxLeft) {
                    this.left = maxLeft
                } else {
                    this.left = left
                }

                this.top = e.clientY
                this.selectedTag = tag
                this.menuVisible = true
            },
            closeMenu() {
                this.menuVisible = false
            }
        }
    }
</script>

<style lang="less">
    .tags-view{
        height: 34px;
        background-color: #ffffff;
        padding: 0px;
        display: flex;
        flex-direction: row;
        align-items: center;
        position: relative;
        overflow-x: hidden;
        .tags-scoll-view{
            white-space: nowrap;
            display: inline-block;
            position: relative;
            .tags-view-item {
                display: inline-block;
                position: relative;
                cursor: pointer;
                height: 26px;
                line-height: 26px;
                border: 1px solid #d8dce5;
                color: #495060;
                background: #fff;
                padding: 0 5px;
                font-size: 13px;
                margin-left: 3px;
                &:first-of-type {
                    margin-left: 3px;
                }
                &:last-of-type {
                    margin-right: 5px;
                }
                &.active {
                    background-color: #409EFF;
                    color: #fff;
                    border-color: #409EFF;
                    &::before {
                        content: '';
                        background: #fff;
                        display: inline-block;
                        width: 8px;
                        height: 8px;
                        border-radius: 50%;
                        position: relative;
                        margin-right: 2px;
                    }
                }
            }
        }
    }

    .contextmenu {
        margin: 0;
        background: #fff;
        z-index: 3000;
        position: fixed;
        list-style-type: none;
        padding: 5px 0;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 400;
        color: #333;
        box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);
        li {
            margin: 0;
            padding: 7px 16px;
            cursor: pointer;
            &:hover {
                background: #eee;
            }
        }
    }
</style>
