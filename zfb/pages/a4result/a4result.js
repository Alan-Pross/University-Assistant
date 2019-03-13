Page({
  data: {
    url: 'http://cet.etest.net.cn/Home/DownTestTicket?SID=',
    SID: ''
  },
  handleClick: function() {
    wx.setClipboardData({
      data: this.data.url + this.data.SID,
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
    this.setData({
      SID: option.SID
    })
  }
})