function getAverage (str) {
    let arr = str.split(',').map(value => Number(value));
    const sum = arr.reduce((a, b) => a + b, 0);
    const avg = (sum / arr.length) || 0;

    return avg;
}

function getStandardDeviation (str) {
    let arr = str.split(',').map(value => Number(value));
    const n = arr.length;
    const mean = arr.reduce((a, b) => a + b) / n;

    return Math.sqrt(arr.map(x => Math.pow(x - mean, 2)).reduce((a, b) => a + b) / n);
}

function data (str) {
    return getAverage(str).toExponential(3) + ' (' + getStandardDeviation(str).toExponential(3) + ')';
}