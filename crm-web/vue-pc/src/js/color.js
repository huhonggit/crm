export function selectMainColorTo16Radix(img, width, height, callback) {
  var averageColorArr = selectMainColor(img, width, height);
  callback(`#${averageColorArr.map(item => item.toString(16)).join("")}`);
}

export function selectMainColorTo10Radix(img, width, height, callback) {
  var averageColorArr = selectMainColor(img, width, height);
  callback(`rgba(${averageColorArr.join(",")})`);
}

export function selectMainColor(img, selectWidth, selectHeight, callback) {
  var canvas = document.createElement("canvas"),
    ctx = canvas.getContext("2d"),
    width = 0,
    height = 0,
    imageData = null,
    length = 0,
    blockSize = 1,
    cubeArr = [];

  width = canvas.width = img.width;
  height = canvas.height = img.height;

  ctx.drawImage(img, 0, 0, width, height);

  imageData = ctx.getImageData(
    0,
    0,
    selectWidth || width,
    selectHeight || height
  ).data;

  var total = imageData.length / 4;

  var rMin = 255,
    rMax = 0,
    gMin = 255,
    gMax = 0,
    bMin = 255,
    bMax = 0;

  // 获取范围
  for (var i = 0; i < total; i++) {
    var red = imageData[i * 4],
      green = imageData[i * 4 + 1],
      blue = imageData[i * 4 + 2];

    if (red < rMin) {
      rMin = red;
    }

    if (red > rMax) {
      rMax = red;
    }

    if (green < gMin) {
      gMin = green;
    }

    if (green > gMax) {
      gMax = green;
    }

    if (blue < bMin) {
      bMin = blue;
    }

    if (blue > bMax) {
      bMax = blue;
    }
  }

  var colorRange = [[rMin, rMax], [gMin, gMax], [bMin, bMax]];
  var colorBox = new ColorBox(colorRange, total, imageData);

  var colorBoxArr = queueCut([colorBox], 2); // 切割次数，越高精度越高，但是取色为单一色调时会有bug，暂时先切割两次

  var colorArr = [];
  for (var j = 0; j < colorBoxArr.length; j++) {
    colorBoxArr[j].total && colorArr.push(colorBoxArr[j].getColor());
  }

  var averageColorArr = colorArr
    .reduce(
      (prev, cur) => {
        return [prev[0] + cur[0], prev[1] + cur[1], prev[2] + cur[2]];
      },
      [0, 0, 0]
    )
    .map(item => Number.parseInt(item / colorArr.length), 10);

  callback && callback(averageColorArr);
  return averageColorArr;
}

function ColorBox(colorRange, total, data) {
  this.colorRange = colorRange;
  this.total = total;
  this.data = data;
  this.volume =
    (colorRange[0][1] - colorRange[0][0]) *
    (colorRange[1][1] - colorRange[1][0]) *
    (colorRange[2][1] - colorRange[2][0]);
  this.rank = this.total * this.volume;
}

ColorBox.prototype.getColor = function() {
  var total = this.total;
  var data = this.data;

  var redCount = 0,
    greenCount = 0,
    blueCount = 0;

  for (var i = 0; i < total; i++) {
    redCount += data[i * 4];
    greenCount += data[i * 4 + 1];
    blueCount += data[i * 4 + 2];
  }

  return [
    parseInt(redCount / total),
    parseInt(greenCount / total),
    parseInt(blueCount / total)
  ];
};

// 获取切割边
function getCutSide(colorRange) {
  // r:0,g:1,b:2
  var arr = [];
  for (var i = 0; i < 3; i++) {
    arr.push(colorRange[i][1] - colorRange[i][0]);
  }
  return arr.indexOf(Math.max(arr[0], arr[1], arr[2]));
}

// 切割颜色范围
function cutRange(colorRange, colorSide, cutValue) {
  var arr1 = [];
  var arr2 = [];
  colorRange.forEach(function(item) {
    arr1.push(item.slice());
    arr2.push(item.slice());
  });
  arr1[colorSide][1] = cutValue;
  arr2[colorSide][0] = cutValue;
  return [arr1, arr2];
}

// 找到出现次数为中位数的颜色
function getMedianColor(colorCountMap, total) {
  var arr = [];
  for (var key in colorCountMap) {
    arr.push({
      color: parseInt(key),
      count: colorCountMap[key]
    });
  }

  var sortArr = __quickSort(arr);
  var medianCount = 0;
  var medianColor = 0;
  var medianIndex = Math.floor(sortArr.length / 2);

  for (var i = 0; i < medianIndex; i++) {
    medianCount += sortArr[i].count;
  }
  if (medianCount === 0 && sortArr.length === 1) {
    medianCount = sortArr[0].count
  }
  return {
    color: parseInt(sortArr[medianIndex].color),
    count: medianCount
  };

  function __quickSort(arr) {
    if (arr.length <= 1) {
      return arr;
    }
    var pivotIndex = Math.floor(arr.length / 2),
      pivot = arr.splice(pivotIndex, 1)[0];

    var left = [],
      right = [];
    for (var i = 0; i < arr.length; i++) {
      if (arr[i].count <= pivot.count) {
        left.push(arr[i]);
      } else {
        right.push(arr[i]);
      }
    }
    return __quickSort(left).concat([pivot], __quickSort(right));
  }
}

// 切割颜色盒子
function cutBox(colorBox) {
  var colorRange = colorBox.colorRange,
    cutSide = getCutSide(colorRange),
    colorCountMap = {},
    total = colorBox.total,
    data = colorBox.data;

  // 统计出各个值的数量
  for (var i = 0; i < total; i++) {
    var color = data[i * 4 + cutSide];

    if (colorCountMap[color]) {
      colorCountMap[color] += 1;
    } else {
      colorCountMap[color] = 1;
    }
  }
  var medianColor = getMedianColor(colorCountMap, total);
  var cutValue = medianColor.color;
  var cutCount = medianColor.count;
  var newRange = cutRange(colorRange, cutSide, cutValue);
  var box1 = new ColorBox(newRange[0], cutCount, data.slice(0, cutCount * 4)),
    box2 = new ColorBox(
      newRange[1],
      total - cutCount,
      data.slice(cutCount * 4)
    );
  return [box1, box2];
}

// 队列切割
function queueCut(queue, num) {
  while (queue.length < num) {
    queue.sort(function(a, b) {
      return a.rank - b.rank;
    });
    var colorBox = queue.pop();
    var result = cutBox(colorBox);
    queue = queue.concat(result);
  }

  return queue.slice(0, 8);
}
