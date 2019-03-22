const { $Message } = require('../../dist/base/index');
Page({
  data: {
    value1: ''
  },
  xhsInput: function (event) {
    this.setData({
      value1: event.detail.detail.value
    })
  },
  onLoad: function () {
  },
  handleClick: function () {
    if (this.data.value1.length != 12) {
      $Message({
        content: '学号为12位数字',
        type: 'warning'
      });
      return;
    }
    wx.navigateTo({
      url: '../a2result/a2result?xh=' + this.data.value1
    })
  }
})
