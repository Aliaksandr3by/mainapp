import PropTypes from "prop-types";
import React, {Component} from "react";
import {instanceOf} from "prop-types";
import {withCookies, Cookies} from "react-cookie";


/**
 * Главный компонент
 * @componentName Main class
 * @description Главный компонент
 */
class App extends Component {
	static propTypes = {
		SetControlActionURL: PropTypes.object.isRequired,
		cookies: instanceOf(Cookies).isRequired,
	};

	//является первой функцией, вызываемой при установке компонента
	constructor(props) {
		super(props);
		this.state = {
			SetControlActionURL: this.props.SetControlActionURL,
			items: this.getAll(),
			isLoaded: false,
			status: null,
			error: null,
			message: null,
		};
	}

	//взывается сразу же после отображения компонента на экране приведут к запуску жизненного цикла обновления и к повторному отображению компонента на экране
	async componentDidMount() {
		console.log(`componentDidMount`);
	}

	//непосредственно перед удалением его с экрана
	async componentWillUnmount() {
	}

	//предикат, способный отменить обновление;
	async shouldComponentUpdate(nextProps, nextState) {

	}

	//вызывается сразу же после выполнения обновления, после вызова метода отображения render ;
	async componentDidUpdate() {

	}

	/**
	 *Метод получает перечень избранных товаров авторизованного пользователя
	 *
	 * @memberof App
	 */
	getAll = async (e) => {
		try {
			const data = {
				"employeeId": 3,
				"firstName": "qwe",
				"lastName": "qweqwe",
				"departmentId": 1,
				"jobTitle": "",
				"gender": "FEMALE"
			};

			const response = await fetch(`${this.props.SetControlActionURL.urlControlActionGreeting}`, {
				method: "GET", // *GET, POST, PUT, DELETE, etc.
				mode: "cors", // no-cors, cors, *same-origin
				cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
				credentials: "same-origin", // include, *same-origin, omit
				headers: {
					"Content-Type": "application/json",
				},
				redirect: "follow", // manual, *follow, error
				referrer: "no-referrer", // no-referrer, *client
				// body: JSON.stringify(data),
			});

			const result = await response.json();

			console.dir(result);

			if (Array.isArray(result) && result.length >= 0) {

				this.setState({
					items: result,
					isLoaded: true,
				});
			}
			return result;

		} catch (error) {
			console.error(error);
		}
	};

	deleteById = async (e) => {
		try {
			const id = e.currentTarget.dataset.employee;
			const data = {
				"employeeId": Number(id),
			};

			const response = await fetch(`${this.props.SetControlActionURL.ActionControlDeleteEmployee}`, {
				method: "DELETE", // *GET, POST, PUT, DELETE, etc.
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(data),
			});

			const result = await response;

			console.log(result);

			if (Number(result.status) === 204) {
				const a = this.state.items.filter(item => item.employeeId !== Number(id));
				this.setState((state, props) => {
					return {
						items: [...a]
					};
				});
			}

			return result;

		} catch (error) {
			console.error(error);
		}
	};

	stateChangeResult = async (array, name) => {
		await this.setState((state, props) => {
			if (Array.isArray(array)) {
				return {
					[name]: [...state[name], ...array]
				};
			} else {
				return {
					[name]: [...state[name], array]
				};
			}
		});
	};

	render() {

		const {SetControlActionURL, items, isLoaded, error} = this.state;

		if (isLoaded && items.length > 0) {
			return (
				<React.Fragment>
					{
						items.map((item, i) => {
							return (<div className={"employee"} key={item["employeeId"]}>
								<button data-employee={item["employeeId"]} type="button" className="btn"
										onClick={(e) => this.deleteById(e)}>{`delete ${item["firstName"]}`}</button>
								<div>{
									Object.keys(item).map((element, i) => {
										return (<p key={`${element}`}>{item[element]}</p>);
									})
								}</div>
							</div>);
						})
					}
				</React.Fragment>
			);
		} else if (error) {
			return (
				<React.Fragment>
					<p>error</p>
				</React.Fragment>
			);
		} else if (!isLoaded) {
			return (
				<React.Fragment>
					<p>Loading</p>
				</React.Fragment>
			);
		} else if (items.length === 0) {
			return (
				<React.Fragment>
					<p>length is 0</p>
				</React.Fragment>
			);
		}
	}
}

export default withCookies(App);



