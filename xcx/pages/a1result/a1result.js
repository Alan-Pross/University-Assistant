Page({
  data: {
    Rate: '',
    kWH: '',
    qsh: ''
  },
  onLoad: function(option) {
    this.setData({
      Rate: option.Rate,
      kWH: option.kWH,
      qsh: option.qsh
    })
  }
})