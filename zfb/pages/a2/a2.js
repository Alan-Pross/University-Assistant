Page({
  data: {
    value1: ''
  },
  onLoad: function () {
  },
  handleClick: function () {
    $Message({
      content: '此功能将于不久后开通，敬请期待',
      type: 'warning'
    });
  }
})
