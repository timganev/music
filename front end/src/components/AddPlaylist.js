import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Checkbox from "@material-ui/core/Checkbox";

// import { Slider } from "react-native";

class AddPlaylist extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: this.props.username,
      title: "",
      sameartist: false,
      topranks: false,
      Dance: 0,
      Rock: 0,
      Pop: 0,
      value: 50,
      data: []
    };
  }

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleCheck = name => event => {
    this.setState({ [name]: event.target.checked });
  };

  // Save playlist and close modal form
  handleSubmit = event => {
    event.preventDefault();
    var duration = this.props.duration * 60;
    var Dance = Math.abs(this.state.Dance);
    var Rock = Math.abs(this.state.Rock);
    var Pop = Math.abs(this.state.Pop);
    var sum = Dance + Rock + Pop;

    Dance = Math.round((Dance / sum) * duration);
    Rock = Math.round((Rock / sum) * duration);
    Pop = Math.round((Pop / sum) * duration);

    var arr = [];

    if (Dance > 0) {
      arr.push({ duration: Dance, genre: "Dance" });
    }
    if (Rock > 0) {
      arr.push({ duration: Rock, genre: "Rock" });
    }
    if (Pop > 0) {
      arr.push({ duration: Pop, genre: "Pop" });
    }

    this.setState({ data: arr });

    var newPlaylist = {
      username: this.state.username,
      title: this.state.title,
      sameartist: this.state.sameartist,
      topranks: this.state.topranks,
      data: arr
    };
    this.props.addPlaylist(newPlaylist);
    this.refs.addDialog.hide();
  };

  cancelSubmit = event => {
    event.preventDefault();
    this.refs.addDialog.hide();
  };

  render() {
    return (
      <div>
        <SkyLight hideOnOverlayClicked ref="addDialog">
          <h4>New Playlist {this.props.duration} min</h4>

          <form>
            <br />
            <TextField
              label="Title"
              placeholder="title"
              name="title"
              onChange={this.handleChange}
            />
            <br />
            <TextField
              size="small"
              type="number"
              min="0"
              name="Dance"
              label="Dance"
              value={this.state.Dance}
              onChange={this.handleChange}
              variant="outlined"
              style={{ width: 63 }}
            />
            <TextField
              size="small"
              type="number"
              min="0"
              name="Rock"
              label="Rock"
              value={this.state.Rock}
              onChange={this.handleChange}
              variant="outlined"
              style={{ width: 63 }}
            />
            <TextField
              size="small"
              type="number"
              min="0"
              name="Pop"
              label="Pop"
              value={this.state.Pop}
              onChange={this.handleChange}
              variant="outlined"
              style={{ width: 63 }}
            />
            <br />
            same artist
            {this.state.sameartist}
            <Checkbox
              label="sameartist"
              checked={this.state.sameartist}
              onChange={this.handleCheck("sameartist")}
              value="sameartist"
            />
            top ranks
            <Checkbox
              label="topranks"
              checked={this.state.topranks}
              onChange={this.handleCheck("topranks")}
              value="topranks"
            />
            <br />
            <Button
              variant="outlined"
              style={{ marginRight: 10 }}
              color="primary"
              onClick={this.handleSubmit}
            >
              Save
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              onClick={this.cancelSubmit}
            >
              Cancel
            </Button>
          </form>
        </SkyLight>
        <div>
          <Button
            size="small"
            variant="flat"
            color="primary"
            variant="outlined"
            onClick={() => this.refs.addDialog.show()}
          >
            New Playlist
          </Button>
        </div>
      </div>
    );
  }
}

export default AddPlaylist;
