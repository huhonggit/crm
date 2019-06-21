<template>
    <div class="tinymce-editor">
        <editor v-model="myValue"
                :init="init"
                :disabled="disabled"
                :style="{height:height,width:width}"
                @onClick="onClick">
        </editor>
    </div>
</template>

<script>
    import tinymce from 'tinymce/tinymce'
    import Editor from '@tinymce/tinymce-vue'
    import 'tinymce/themes/silver'

    // 编辑器插件plugins
    // 更多插件参考：https://www.tiny.cloud/docs/plugins/
    import 'tinymce/plugins/image'// 插入上传图片插件
    import 'tinymce/plugins/media'// 插入视频插件
    import 'tinymce/plugins/table'// 插入表格插件
    import 'tinymce/plugins/lists'// 列表插件
    import 'tinymce/plugins/wordcount'// 字数统计插件
    import 'tinymce/plugins/textcolor'
    import 'tinymce/plugins/colorpicker'
    import 'tinymce/plugins/link'
    import 'tinymce/plugins/fullscreen'
    import 'tinymce/plugins/importcss'
    import 'tinymce/plugins/hr'
    import 'tinymce/plugins/code'
    import 'tinymce/plugins/contextmenu'
    import 'tinymce/plugins/preview'
    import 'tinymce/plugins/advlist'

    import uploadApi from "../../js/api/UploadFileApi";


    export default {
        components: {
            Editor
        },
        props: {
            width: {
                type: String,
                default: '100%'
            },
            height: {
                type: String,
                default: '400px'
            },
            //传入一个value，使组件支持v-model绑定
            value: {
                type: String,
                default: ''
            },
            disabled: {
                type: Boolean,
                default: false
            },
            plugins: {
                type: [String, Array],
                default: `
                        importcss
                        image
                        code
                        table
                        advlist
                        fullscreen
                        link
                        lists
                        textcolor
                        colorpicker
                        hr
                        preview
                      `
            },
            toolbar: {
                type: [String, Array],
                default: `styleselect |
                         fontselect |
                         formatselect |
                         fontsizeselect |
                         forecolor backcolor |
                         bold italic underline strikethrough |
                         insertfile link image |
                         table |
                         alignleft aligncenter alignright alignjustify |
                         outdent indent |
                         numlist bullist |
                         preview removeformat  hr |
                         paste code | undo redo |
                         fullscreen `
            }
        },
        data() {
            return {
                //初始化配置
                init: {
                    language_url: '/tinymce/langs/zh_CN.js', ////导入中文语言文件
                    language: 'zh_CN', //语言设置中文
                    // skin_url: '/tinymce/skins/ui/oxide-dark',//暗色系
                    skin_url: '/tinymce/skins/ui/oxide',//在node_modules中tinymce文件夹中
                    height: 500,
                    // width: 1000,
                    //是否可拉伸
                    resize: true,
                    toolbar: this.toolbar, //需要的工具栏
                    plugins: this.plugins,
                    // CONFIG
                    forced_root_block: 'p',
                    branding: false,//去除右下角的'由tinymce驱动'
                    elementpath: false,//左下角的当前标签路径
                    force_p_newlines: true,
                    importcss_append: true,
                    // CONFIG: ContentStyle 这块很重要， 在最后呈现的页面也要写入这个基本样式保证前后一致， `table`和`img`的问题基本就靠这个来填坑了
                    content_style: `
                        *                         { padding:0; margin:0; }
                        html, body                { height:100%; }
                        img                       { max-width:100%; display:block;height:auto; }
                        a                         { text-decoration: none; }
                        iframe                    { width: 100%; }
                        p                         { line-height:1.6; margin: 0px; }
                        table                     { word-wrap:break-word; word-break:break-all; max-width:100%; border:none; border-color:#999; }
                        .mce-object-iframe        { width:100%; box-sizing:border-box; margin:0; padding:0; }
                        ul,ol                     { list-style-position:inside; }
                      `,
                    insert_button_items: 'image link | inserttable',
                    // CONFIG: Paste
                    paste_retain_style_properties: 'all',
                    paste_word_valid_elements: '*[*]',        // word需要它
                    paste_data_images: true,                  // 粘贴的同时能把内容里的图片自动上传，非常强力的功能
                    paste_convert_word_fake_lists: false,     // 插入word文档需要该属性
                    paste_webkit_styles: 'all',
                    paste_merge_formats: true,
                    nonbreaking_force_tab: false,
                    paste_auto_cleanup_on_paste: false,
                    // CONFIG: Font
                    fontsize_formats: '10px 11px 12px 14px 16px 18px 20px 24px',
                    // CONFIG: StyleSelect
                    style_formats: [
                        {
                            title: '首行缩进',
                            block: 'p',
                            styles: {'text-indent': '2em'}
                        },
                        {
                            title: '行高',
                            items: [
                                {title: '1', styles: {'line-height': '1'}, inline: 'span'},
                                {title: '1.5', styles: {'line-height': '1.5'}, inline: 'span'},
                                {title: '2', styles: {'line-height': '2'}, inline: 'span'},
                                {title: '2.5', styles: {'line-height': '2.5'}, inline: 'span'},
                                {title: '3', styles: {'line-height': '3'}, inline: 'span'}
                            ]
                        }
                    ],
                    // FontSelect
                    font_formats: `
                        微软雅黑=微软雅黑;
                        宋体=宋体;
                        黑体=黑体;
                        仿宋=仿宋;
                        楷体=楷体;
                        隶书=隶书;
                        幼圆=幼圆;
                        Andale Mono=andale mono,times;
                        Arial=arial, helvetica,
                        sans-serif;
                        Arial Black=arial black, avant garde;
                        Book Antiqua=book antiqua,palatino;
                        Comic Sans MS=comic sans ms,sans-serif;
                        Courier New=courier new,courier;
                        Georgia=georgia,palatino;
                        Helvetica=helvetica;
                        Impact=impact,chicago;
                        Symbol=symbol;
                        Tahoma=tahoma,arial,helvetica,sans-serif;
                        Terminal=terminal,monaco;
                        Times New Roman=times new roman,times;
                        Trebuchet MS=trebuchet ms,geneva;
                        Verdana=verdana,geneva;
                        Webdings=webdings;
                        Wingdings=wingdings,zapf dingbats`,
                    // Tab
                    tabfocus_elements: ':prev,:next',
                    object_resizing: true,
                    // Image
                    imagetools_toolbar: 'rotateleft rotateright | flipv fliph | editimage imageoptions',
                    // 上传图片
                    // 其中 blobinfo 是一个对象，包含上传文件的信息：
                    // success 和 failure 是函数，上传成功的时候向 success 传入一个图片地址，失败的时候向 failure 传入报错信息
                    images_upload_handler: (blobInfo, success, failure) => {
                        uploadPic();

                        function uploadPic() {
                            let formdata = new FormData();
                            formdata.set('upload_file', blobInfo.blob());
                            uploadApi.uploadImages(formdata).then(res => {
                                console.log(res);
                                success(res.data)
                            }).catch()
                        }

                    }
                },
                myValue: this.value
            }
        },
        mounted() {
            tinymce.init({})
        },
        methods: {
            //添加相关的事件，可用的事件参照文档=> https://github.com/tinymce/tinymce-vue => All available events
            //需要什么事件可以自己增加
            onClick(e) {
                this.$emit('onClick', e, tinymce)
            },
            //可以添加一些自己的自定义事件，如清空内容
            clear() {
                this.myValue = ''
            }

        },
        watch: {
            value(newValue) {
                this.myValue = newValue
            },
            myValue(newValue) {
                this.$emit('input', newValue)
            }
        }
    }

</script>
<style scoped>
</style>
