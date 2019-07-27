
import PropTypes from "prop-types";
import React from "react";
import { CookiesProvider } from "react-cookie";

import App from "./App";

/**
 * Точка входа
 * @param {*} props 
 */
const Root = (props) => {
    const {SetControlActionURL = {}} = props;
    return (
        // https://www.npmjs.com/package/react-cookie
        <CookiesProvider>
            <App SetControlActionURL={SetControlActionURL} />
        </CookiesProvider>
    );
};

Root.propTypes = {
    SetControlActionURL: PropTypes.object.isRequired,
};

export default Root;