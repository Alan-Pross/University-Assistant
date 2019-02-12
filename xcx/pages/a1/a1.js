const { $Message } = require('../../dist/base/index');
Page({
  data: {
    switch1: true,
    value1: 1,
    value2: ''
  },
  onLoad: function () {
  },
  handleChange1({ detail }) {
    this.setData({
      value1: detail.value
    })
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
  },
  handleClick: function () {
    $Message({
      content: '抱歉，此功能未开通',
      type: 'warning'
    });
  }
})
