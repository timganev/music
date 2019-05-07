import React, { Component } from "react";
import "./App.css";
import Log from "./components/users/Log";
import Reg from "./components/users/Reg";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import SignedInLinks from "./SignedInLinks";
import SignedOutLinks from "./SignedOutLinks";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: null
    };
  }

  //update username
  updateUsername(value) {
    this.setState({ username: value });
  }

  render() {
    const links =
      this.state.username == null ? <SignedOutLinks /> : <SignedInLinks />;

    return (
      <div className="App">
        <AppBar className="nav-wrapper grey darken-3" fixed="top" scrolling>
          <Toolbar>Music App </Toolbar>
        </AppBar>

        <Log updateUsername={this.updateUsername.bind(this)} />
        <Log updateUsername={this.updateUsername.bind(this)} />
        {links}
        {/* <Reg /> */}
      </div>
    );
  }
}

export default App;
