Page({
  data: {
    r1: '',
    r2: '',
    r3: '',
    r6: '',
    r7: '',
    r8: '',
    r9: '',
    r12: '',
    zkzh: ''
  },
  onLoad: function(option) {
    this.setData({
      r1: option.r1,
      r2: option.r2,
      r3: option.r3,
      r6: option.r6,
      r7: option.r7,
      r8: option.r8,
      r9: option.r9,
      r12: option.r12,
      zkzh: option.zkzh
    })
  }
})