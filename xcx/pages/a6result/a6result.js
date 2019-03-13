Page({
  data: {
    r1: '',
    r2: '',
    r3: '',
    r4: '',
    r5: '',
    r6: ''
  },
  onLoad: function(option) {
    this.setData({
      r1: option.r1,
      r2: option.r2,
      r3: option.r3,
      r4: option.r4,
      r5: option.r5,
      r6: option.r6
    })
  }
})