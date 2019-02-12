//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    percent: 0,
    status: 'normal',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    if (this.data.status == 'success') {
      wx.redirectTo({
        url: '../second/second?id=' + this.data.userInfo.nickName
      })
    }
  },
  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      },
      this.goProgress())
    } else {
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        },
        this.goProgress())
      }
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  goProgress: function () {
    var self = this;
    setInterval(function () {
      if (self.data.percent === 100) return;
      self.setData({
        percent: self.data.percent + 10
      });
      if (self.data.percent === 100) {
        self.setData({
          status: 'success'
        },
          setTimeout(function () {
            wx.redirectTo({
              url: '../second/second?id=' + self.data.userInfo.nickName
              //url: '../a3result/a3result'
            })
          }, 100)
        );
      }
    }, 200)
  }
})
