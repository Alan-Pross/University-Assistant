const { $Message } = require('../../dist/base/index');
Page({
  data: {
    value1: ''
  },
  onLoad: function () {
  },
  handleClick: function () {
    $Message({
      content: '抱歉，此功能未完成',
      type: 'warning'
    });
  }
})
