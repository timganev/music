import React, { Component } from "react";
import ReactTable from "react-table";
import "react-table/react-table.css";
import { SERVER_URL } from "../constants.js";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Snackbar from "@material-ui/core/Snackbar";

import Tracks from "./Tracks";

import Route from "./Route.js";
import TextField from "@material-ui/core/TextField";

class Playlists extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: this.props.username,
      playlists: [],
      user: "",
      open: false,
      message: "",
      duration: 0
    };
  }

  componentDidMount() {
    this.fetchAllPlayLists();
  }

  // Fetch all playlists
  fetchAllPlayLists = async () => {
    this.setState({
      playlists: []
    });

    const token = sessionStorage.getItem("jwt");
    var data = [];
    await fetch(SERVER_URL + "api/playlists", {
      headers: { Authorization: token }
    })
      .then(response => response.json())
      .then(responseData => {
        for (let i = 0; i < responseData._embedded.playlists.length; i++) {
          this.fetchTags(
            responseData._embedded.playlists[i]._links.playlistGenres.href
          ).then(tag => (responseData._embedded.playlists[i].data = tag));

          var joined = this.state.playlists.concat(
            responseData._embedded.playlists[i]
          );

          this.setState({ playlists: joined });
        }

        // console.log(this.state.playlists);
      })
      .catch(err => console.error(err));
  };

  // fechTagsData
  fetchTags = async link => {
    const token = sessionStorage.getItem("jwt");
    var data = "";
    await fetch(link, {
      headers: { Authorization: token }
    })
      .then(response => response.json())
      .then(responseData => {
        for (let i = 0; i < responseData._embedded.genres.length; i++) {
          data = data + responseData._embedded.genres[i].name + " ";
        }
      })
      .catch(err => console.error(err));
    return data;
  };

  customFilter = (filter, row) => {
    const id = filter.pivotId || filter.id;
    if (row[id] !== null && typeof row[id] === "string") {
      return row[id] !== undefined
        ? String(row[id].toLowerCase()).includes(filter.value.toLowerCase())
        : true;
    }
  };

  confirmDelete = link => {
    confirmAlert({
      message: "Are you sure to delete?",
      buttons: [
        {
          label: "Yes",
          onClick: () => this.onDelClick(link)
        },
        {
          label: "No"
        }
      ]
    });
  };

  // Delete list
  onDelClick = link => {
    const token = sessionStorage.getItem("jwt");
    fetch(link, {
      method: "DELETE",
      headers: { Authorization: token }
    })
      .then(res => {
        this.setState({ open: true, message: "Deleted" });
        this.fetchAllPlayLists();
      })
      .catch(err => {
        this.setState({ open: true, message: "Error when deleting" });
        console.error(err);
      });
  };

  //update duration
  updateDuration(value) {
    this.setState({ duration: value });
  }
  // Add new playlist  from child
  addPlaylist(playlist) {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "generate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: token
      },
      body: JSON.stringify(playlist)
    })
      .then(res => this.fetchAllPlayLists())
      .catch(err => console.error(err));
  }

  // Update playlist
  updatePlaylist(playlist, link) {
    const token = sessionStorage.getItem("jwt");
    fetch(link, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: token
      },
      body: JSON.stringify(playlist)
    })
      .then(res => this.setState({ open: true, message: "Changes saved" }))
      .catch(err =>
        this.setState({ open: true, message: "Error when saving" })
      );
  }

  renderEditable = cellInfo => {
    return (
      <div
        style={{ backgroundColor: "#fafafa" }}
        contentEditable
        suppressContentEditableWarning
        onBlur={e => {
          const data = [...this.state.playlists];
          data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
          this.setState({ playlists: data });
        }}
        dangerouslySetInnerHTML={{
          __html: this.state.playlists[cellInfo.index][cellInfo.column.id]
        }}
      />
    );
  };

  handleClose = (event, reason) => {
    this.setState({ open: false });
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  render() {
    const columns = [
      {
        Header: "Title",
        accessor: "title"
      },
      // {
      //   Header: "Tags",
      //   id: "data",
      //   // accessor: "data"
      //   accessor: d => d.data
      // },
      {
        Header: "Duration",
        accessor: "duration",
        filterable: false
      },
      {
        Header: "Rank",
        accessor: "avgrank",
        filterable: false
      },
      {
        Header: "Image",
        accessor: "image_url",
        filterable: false,
        sortable: false,

        Cell: ({ value }) => (
          <div>
            <img src={value} width="60" height="60" />
          </div>
        )
      },
      {
        Header: "User",
        accessor: "username"
      },
      {
        id: "playbutton",
        sortable: false,
        filterable: false,
        width: 100,
        accessor: "_links.playlistTracks.href",
        Cell: ({ value, row }) => <Tracks value={value} />
      }
    ];

    return (
      <div className="App">
        <h4>Welcome</h4>
        <div m={200}>
          <ReactTable
            data={this.state.playlists}
            columns={columns}
            filterable={true}
            defaultFilterMethod={this.customFilter}
            pageSize={5}
            showPageSizeOptions={false}
            noDataText="Loading!"
            showPaginationBottom
          />
          <Snackbar
            open={this.state.open}
            onClose={this.handleClose}
            autoHideDuration={1500}
            message={this.state.message}
          />
        </div>
      </div>
    );
  }
}

export default Playlists;
