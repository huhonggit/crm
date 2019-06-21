const path = require('path')

module.exports = {
  css: {
    extract: false,
  },
  outputDir: '../src/main/webapp/WEB-INF/ftl',
  indexPath: `index.html`,
  runtimeCompiler: true,
  chainWebpack: config => {

    config.performance.hints(false)

    config.module.rule('svg').exclude.add(path.resolve(__dirname, 'src')).end()
    config.module.rule('icons').
      test(/\.svg$/).
      include.
      add(path.resolve(__dirname, 'src')).
      end().
      use('svg-sprite-loader').
      loader('svg-sprite-loader').
      options({
        symbolId: 'svg-[name]',
      })
  },
  devServer: {
    disableHostCheck: true,
    port: 4000,
    // api转发
    proxy: {
      '/': {
        // 要修改请前往 package.json 中
        target: 'http://127.0.0.1:8888',
        // target: 'https://cmgt.cheesestar.com',
        // 是否转发socket
        ws: false,
        // 更改origin，避免跨域警告
        changeOrigin: true,
      },
    },
  },
}
