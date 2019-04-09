const { $Message } = require('../../dist/base/index');
Page({
  data: {
    switch1: true,
    value1: 1,
    value2: '',
    qsh: ''
  },
  qshsInput: function (event) {
    this.setData({
      value2: event.detail.detail.value
    })
  },
  onLoad: function () {
  },
  handleChange1({ detail }) {
    this.setData({
      value1: detail.value
    })
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
  },
  handleClick: function () {
    var self = this;
    if (this.data.value2.length != 3) {
      $Message({
        content: '请检查寝室号是否正确',
        type: 'warning'
      });
      return;
    }
    if (self.data.switch1) {
      this.setData({
        'qsh': 'BEI' + self.data.value1 + '-' + self.data.value2
      })
    }
    else {
      this.setData({
        'qsh': 'NAN' + self.data.value1 + '-' + self.data.value2
      })
    }
    wx.navigateTo({
      url: '../a1logresult/a1logresult?qsh=' + self.data.qsh
    })
  }
})
