Page({
  data: {
    Rate: '',
    kWH: '',
    W: '',
    qsh: ''
  },
  onLoad: function(option) {
    this.setData({
      Rate: option.Rate,
      kWH: option.kWH,
      W: option.W,
      qsh: option.qsh
    })
  }
})