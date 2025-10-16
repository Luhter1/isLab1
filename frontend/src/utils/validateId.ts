const validatorID = (rule, value, callback) => {
    if (!value) {
        callback(new Error('ID is required'))
    } else if (!(!isNaN(value) && (function(x) { return (x | 0) === x; })(parseFloat(value))) || value <= 0) {
        callback(new Error('ID must be a positive integer'))
    } else {
        callback()
    }
}

export default validatorID