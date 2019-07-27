/*global __dirname*/

//const launch = require("./Properties/launchSettings.json");
//const _mode = String(launch.profiles["IIS Express"].environmentVariables.ASPNETCORE_ENVIRONMENT).toLowerCase();
//console.log(_mode);

const path = require("path");
const webpack = require("webpack"); //to access built-in plugins
const UglifyJsPlugin = require("uglifyjs-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const OptimizeCSSAssetsPlugin = require("optimize-css-assets-webpack-plugin");
const HtmlWebpackPlugin = require("html-webpack-plugin");
// const WebpackMd5Hash = require("webpack-md5-hash");
const CleanWebpackPlugin = require("clean-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = (env, argv) => {
	console.table(env);
	console.table(argv);
	const devMode = Boolean(env.development);
	const mode = env.production ? "production" : "development";
	const paths = {
		SRC:    path.resolve(__dirname, "src/main/"),
		VIEW:   path.resolve(__dirname, "src/main/html/"),
		PUBLIC:   path.resolve(__dirname, "target/classes/static"),
		STATIC:   path.resolve(__dirname, "src/main/resources/static"),
		TMP:    path.resolve(__dirname, "src/main/resources/templates/"),
	};
	console.table(paths);
	return {
		mode: mode,
		devtool: false,
		watch: devMode,
		target: env.platform || "web",
		entry: {
			app: `${paths.SRC}/js/index.js`
		},
		output: {
			path: devMode ? `${paths.STATIC}` : `${paths.STATIC}`,
			filename: devMode ? `js/[name].${mode}.bundle.js` : `js/[name].${mode}.bundle.[contenthash].js`,
			publicPath: path.PUBLIC
		},
		optimization: {
			minimizer: [
				new UglifyJsPlugin({
					cache: true,
					parallel: true,
					uglifyOptions: {
						compress: false,
						ie8: false,
						toplevel: true,
						mangle: true
					},
					extractComments: false,
					sourceMap: true
				}),
				new OptimizeCSSAssetsPlugin({})
			]
		},
		module: {
			//Loaders are evaluated/executed from right to left.
			rules: [
				{
					test: /\.(js|jsx)$/,
					exclude: /(node_modules|bower_components)/,
					use: [
						"babel-loader",
					],
				},
				{
					test: /\.(png|jpg)$/,
					exclude: /(node_modules|bower_components)/,
					use: [
						"file-loader",
					],
				},
				{
					test: /\.(sa|sc|c)ss$/,
					exclude: /(node_modules|bower_components)/,
					use: [
						{loader: devMode ? "style-loader" : MiniCssExtractPlugin.loader,},
						{
							loader: "css-loader", // translates CSS into CommonJS modules
							options: {
								sourceMap: true
							}
						},
						{
							loader: "postcss-loader", // Run post css actions
							options: {
								plugins: function () { // post css plugins, can be exported to postcss.config.js
									return [
										require("precss"),
										require("autoprefixer"),
										require("css-mqpacker"),
										require("cssnano")({
											preset: "default",
											discardComments: {
												removeAll: !devMode
											}
										}),
									];
								},
								sourceMap: true
							}
						},
						{
							loader: "sass-loader", // compiles Sass to CSS
							options: {
								sourceMap: true
							}
						}
					]
				}
			],
		},
		plugins: [
			// new webpack.ProvidePlugin({
			//     React: "react",
			//     ReactDOM: "react-dom",
			//     PropTypes: "prop-types",
			//     M: "materialize-css"
			// }),
			new webpack.SourceMapDevToolPlugin({
				filename: "[file].map",
				//lineToLine: true,
				// noSources: false,
			}),
			// new WebpackMd5Hash(),
			new CleanWebpackPlugin({
				cleanOnceBeforeBuildPatterns: devMode
					? ["**/*.js", "*****/*", "**/*.map", "***/*.css", "!favicon.ico"]
					: ["**/*", "!favicon.ico"],
				dry: false, //тру эмулирует удаление
				verbose: true,
				cleanStaleWebpackAssets: false,
			}),
			new MiniCssExtractPlugin({
				filename: devMode ? `[name].${mode}.bundle.css` : `css/[name].${mode}.bundle.[contenthash].css`,
				chunkFilename: devMode ? `[id].${mode}.bundle.css` : "css/[id].[contenthash].css"
			}),
			new HtmlWebpackPlugin({
				inject: false,
				hash: false,
				template: `${paths.VIEW}/index.html`,
				filename: devMode ? `${paths.STATIC}/index.html` : `${paths.STATIC}/index.html`,
			}),
			new CopyWebpackPlugin([
				{from: `${paths.SRC}/images`, to: `${paths.STATIC}/image`},
				/* данные копируются в папку билд только после билда, как отлаживать JS */
				{from: `${paths.STATIC}/js`, to: `${paths.PUBLIC}/js`},
				{from: `${paths.STATIC}/css`, to: `${paths.PUBLIC}/css`},
				{from: `${paths.STATIC}/image`, to: `${paths.PUBLIC}/image`},
			])
		]
	};
};
