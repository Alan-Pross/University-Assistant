const { $Toast } = require('../../dist/base/index');
Page({
  data: {
    xh: '',
    shengao: '',
    tizhong: '',
    feihuo: '',
    m50: '',
    tiaoyuan: '',
    m1000: '',
    tiqian: '',
    yinti: '',
    stizhong: '',
    sfeihuo: '',
    sm50: '',
    stiaoyuan: '',
    sm1000: '',
    stiqian: '',
    syinti: '',
    s: '',
    a2: '/a2?xh='
  },
  onLoad: function(option) {
    this.setData({
      xh: option.xh,
    })
    var self = this;
    $Toast({
      content: '查询中',
      type: 'loading',
      duration: 0
    });
    var network = require('../../tools/network.js')
    network.getrequest(self.data.a2 + self.data.xh, null, function (res) {
      $Toast.hide()
      console.log(res)
      self.setData({
        shengao: res.shengao,
        tizhong: res.tizhong,
        feihuo: res.feihuo,
        m50: res.m50,
        tiaoyuan: res.tiaoyuan,
        m1000: res.m1000,
        tiqian: res.tiqian,
        yinti: res.yinti,
        stizhong: res.stizhong,
        sfeihuo: res.sfeihuo,
        sm50: res.sm50,
        stiaoyuan: res.stiaoyuan,
        sm1000: res.sm1000,
        stiqian: res.stiqian,
        syinti: res.syinti,
        s: res.s
      })
    }, function (res) {
      console.log(res)
      $Toast.hide()
      $Toast({
        content: '查询失败',
        type: 'error'
      });
    })
  }
})