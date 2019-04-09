const { $Message } = require('../../dist/base/index');
Page({
  data: {
    url: 'http://cjcx.neea.edu.cn/html1/folder/1508/206-1.htm?sid=300',
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