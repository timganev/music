import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
////

import Snackbar from "@material-ui/core/Snackbar";
import Playlists from "../Playlists";
import Users from "./Users";
// import Admin from "./Admin";

import { SERVER_URL } from "../../constants.js";

class Log extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      role: "USER",
      isAuthenticated: false,
      open: false
    };
  }

  login = () => {
    const user = {
      username: this.state.username,
      password: this.state.password
    };
    fetch(SERVER_URL + "login", {
      method: "POST",
      body: JSON.stringify(user)
    })
      .then(res => {
        const jwtToken = res.headers.get("Authorization");
        if (jwtToken !== null) {
          sessionStorage.setItem("jwt", jwtToken);
          this.props.updateUsername(jwtToken);
          this.setState({ isAuthenticated: true });
        } else {
          this.setState({ open: true });
        }
      })
      .catch(err => console.error(err));
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleClose = event => {
    this.setState({ open: false });
  };

  cancelSubmit = event => {
    event.preventDefault();
    this.refs.addDialog.hide();
  };
  //////////////////////////////////
  render() {
    if (this.state.isAuthenticated === true) {
      return (
        <div style={{ margin: "20px" }}>
          <Playlists username={this.state.username} />;
          <Users username={this.state.username} />
          {/* <Admin username={this.state.username} /> */}
          <div />
        </div>
      );
    } else {
      return (
        <div>
          <SkyLight hideOnOverlayClicked ref="addDialog">
            <h3>Login</h3>
            <br />
            <br />
            <form>
              <TextField
                label="username"
                placeholder="username"
                name="username"
                onChange={this.handleChange}
              />
              <br />
              <TextField
                label="password"
                type="password"
                placeholder="password"
                name="password"
                onChange={this.handleChange}
              />

              <br />
              <br />

              <Button
                variant="outlined"
                style={{ marginRight: 10 }}
                color="primary"
                onClick={this.login}
              >
                LOGIN
              </Button>
              <Button
                variant="outlined"
                color="secondary"
                onClick={this.cancelSubmit}
              >
                Cancel
              </Button>
              <Snackbar
                open={this.state.open}
                onClose={this.handleClose}
                autoHideDuration={1500}
                message="Check your username and password"
              />
            </form>
          </SkyLight>
          <div>
            <Button
              variant="raised"
              color="primary"
              style={{ margin: "10px" }}
              onClick={() => this.refs.addDialog.show()}
            >
              Login
            </Button>
          </div>
        </div>
      );
    }
  }
}
export default Log;
