import React, { Component } from "react";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Snackbar from "@material-ui/core/Snackbar";
import MapPoint from "../MapPoint";
import Playlists from "../Playlists";
import Users from "../users/Users";
import { SERVER_URL } from "../../constants.js";

class Ragister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      isAuthenticated: false,
      open: false
    };
  }

  register = () => {
    const user = {
      username: this.state.username,
      password: this.state.password
    };
    fetch(SERVER_URL + "signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(user)
    });
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleClose = event => {
    this.setState({ open: false });
  };

  render() {
    if (this.state.isAuthenticated === true) {
      return (
        <div>
          <MapPoint />
          <Playlists />;
          <Users />
        </div>
      );
    } else {
      return (
        <div>
          <br />
          <TextField
            tpye="text"
            name="username"
            placeholder="Username"
            onChange={this.handleChange}
          />
          <br />
          <TextField
            type="password"
            name="password"
            placeholder="Password"
            onChange={this.handleChange}
          />
          <br />
          <br />
          <Button variant="raised" color="primary" onClick={this.register}>
            Register
          </Button>
          <Snackbar
            open={this.state.open}
            onClose={this.handleClose}
            autoHideDuration={1500}
            message="Invalid your username or password"
          />
        </div>
      );
    }
  }
}

export default Ragister;
