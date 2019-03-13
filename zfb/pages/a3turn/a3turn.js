Page({
  data: {
  },
  handleClick: function() {
    wx.setClipboardData({
      data: 'http://cet.neea.edu.cn/cet',
      success: function (res) {
        $Message({
          content: '复制成功，快粘贴到浏览器下载吧!',
          duration: 0,
          type: 'success'
        });
      }
    })
  },
  onLoad: function(option) {
  }
})