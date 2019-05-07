const { $Message } = require('../../dist/base/index');
Page({
  data: {
    url: '',
    title: ''
  },
  handleClick: function () {
    wx.setClipboardData({
      data: this.data.url,
      success: function (res) {
        $Message({
          content: '复制成功，快粘贴到浏览器吧!',
          duration: 0,
          type: 'success'
        });
      }
    })
  },
  onLoad: function (option) {
    var self = this;
    console.log(option);
    this.setData({
      url: decodeURIComponent(option.url),
      title: decodeURIComponent(option.title)
    })
  }
})