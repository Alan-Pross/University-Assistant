const { $Message } = require('../../dist/base/index');
Page({
  data: {
    url: 'https://ntcecf2.neea.edu.cn/',
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
  }
})