Page({
  data: {
    url: ''
  },
  onLoad: function (option) {
    var self = this;
    this.setData({
      url: 'https://www.jhuncloud.com/a1log.html?qsh=' + option.qsh
    })
  }
})