module.exports = {
    entry: {
        attendance: "./src/attendance/index.js",
        attendanceReport: "./src/attendanceReport/index.js",
        emailControlPanel: "./src/emailControlPanel/index.js",
        parent: "./src/parent/index.js",
        statistics: "./src/statistics/index.js",
        universalPickupCode: "./src/universalPickupCode/index.js"
    },
    output: {
        filename: "../../dist/scripts/[name].js"
    },
    mode: "production",
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: "babel-loader"
            }
        ]
    }
};
