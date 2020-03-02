/*=========================================================================================
  File Name: vue.config.js
  Description: configuration file of vue
  ----------------------------------------------------------------------------------------
  Item Name: Vuexy - Vuejs, HTML & Laravel Admin Dashboard Template
  Author: Pixinvent
  Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/

const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = ['js', 'css']
const isProduction = process.env.NODE_ENV === 'production'

module.exports = {
  publicPath: '/',
  transpileDependencies: [
    'vue-echarts',
    'resize-detector'
  ],
  productionSourceMap: false,
  configureWebpack: {
    plugins: [
      // Ignore all locale files of moment.js
      // new webpack.IgnorePlugin(/^\.\/locale$/, /moment$/),
      // new UglifyJsPlugin({
      //   uglifyOptions: {
      //     compress: {
      //       drop_debugger: true,
      //       drop_console: true
      //     }
      //   },
      //   sourceMap: false,
      //   parallel: true
      // }),
      // 配置compression-webpack-plugin压缩
      new CompressionWebpackPlugin({
        algorithm: 'gzip',
        test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
        threshold: 10240,
        minRatio: 0.8
      })
    ],
    optimization: {
      splitChunks: {
        chunks: 'all'
      }
    }
  },
  devServer: {
    proxy: "http://112.124.3.139:30000/"
  }
}

