const { $Message } = require('../../dist/base/index');
const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    a5result: '/a5result',
    sfzh: '',
    xm: ''
  },
  sfzhsInput: function (event) {
    this.setData({
      sfzh: event.detail.detail.value
    })
  },
  xmsInput: function (event) {
    this.setData({
      xm: event.detail.detail.value
    })
  },
  handleClick: function (option) {
    var self = this;
    if (this.data.sfzh.length != 18) {
      $Message({
        content: '身份证号为18位',
        type: 'warning'
      });
      return;
    }
    if (this.data.xm.length < 2) {
      $Message({
        content: '请填写姓名完整',
        type: 'warning'
      });
      return;
    }
    $Toast({
      content: '加载中',
      type: 'loading',
      duration: 0
    });
    var network = require('../../tools/network.js')
    network.getrequest(self.data.a5result, self.data, function (res) {
      $Toast.hide()
      console.log(res)
      wx.redirectTo({
        url: '../a5result/a5result?r1=' + res.r1 + '&r2=' + res.r2 + '&r3=' + res.r3 + '&r4=' + res.r4 + '&r5=' + res.r5 + '&r6=' + res.r6 + '&time=' + res.time
      })
    }, function () {
      $Toast.hide()
      $Toast({
        content: '查询失败，请检查身份证号和姓名',
        type: 'error'
      });
    })
  },
  onLoad: function (option) {
  }
})