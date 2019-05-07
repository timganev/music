import React, { Component } from "react";
import ReactTable from "react-table";
import "react-table/react-table.css";
import { SERVER_URL } from "../../constants.js";
import { confirmAlert } from "react-confirm-alert";
import "react-confirm-alert/src/react-confirm-alert.css";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Snackbar from "@material-ui/core/Snackbar";

class Users extends Component {
  constructor(props) {
    super(props);
    this.state = { users: [], open: false, message: "" };
  }

  componentDidMount() {
    this.fetchLists();
  }

  updateGenres() {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "genres", {
      // method: "DELETE",
      headers: { Authorization: token }
    });
  }

  // Fetch all users
  fetchLists = () => {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "api/users", {
      headers: { Authorization: token }
    })
      .then(response => response.json())
      .then(responseData => {
        this.setState({
          users: responseData._embedded.users
        });
      })
      .catch(err => console.error(err));
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

  // Delete user
  onDelClick = link => {
    const token = sessionStorage.getItem("jwt");
    fetch(link, {
      method: "DELETE",
      headers: { Authorization: token }
    })
      .then(res => {
        this.setState({ open: true, message: "Deleted" });
        this.fetchLists();
      })
      .catch(err => {
        this.setState({ open: true, message: "Error when deleting" });
        console.error(err);
      });
  };

  // Add new user
  addPlaylist(playlist) {
    const token = sessionStorage.getItem("jwt");
    fetch(SERVER_URL + "api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: token
      },
      body: JSON.stringify(playlist)
    })
      .then(res => this.fetchLists())
      .catch(err => console.error(err));
  }

  // Update user
  updatePlaylist(user, link) {
    const token = sessionStorage.getItem("jwt");
    fetch(link, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: token
      },
      body: JSON.stringify(user)
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
          const data = [...this.state.users];
          data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
          this.setState({ users: data });
        }}
        dangerouslySetInnerHTML={{
          __html: this.state.users[cellInfo.index][cellInfo.column.id]
        }}
      />
    );
  };

  handleClose = (event, reason) => {
    this.setState({ open: false });
  };

  render() {
    const columns = [
      {
        Header: "Username",
        accessor: "username"
      },
      {
        Header: "Role",
        accessor: "role",
        filterable: false,
        Cell: this.renderEditable
      },
      {
        id: "savebutton",
        sortable: false,
        filterable: false,
        width: 100,
        accessor: "_links.self.href",
        Cell: ({ value, row }) => (
          <Button
            size="small"
            variant="flat"
            color="primary"
            variant="outlined"
            style={{ margin: "2px" }}
            onClick={() => {
              this.updatePlaylist(row, value);
            }}
          >
            Save
          </Button>
        )
      },
      {
        id: "delbutton",
        sortable: false,
        filterable: false,
        width: 100,
        accessor: "_links.self.href",
        Cell: ({ value }) => (
          <Button
            size="small"
            variant="flat"
            color="secondary"
            variant="outlined"
            style={{ margin: "2px" }}
            onClick={() => {
              this.confirmDelete(value);
            }}
          >
            Delete
          </Button>
        )
      }
    ];

    return (
      <div className="App">
        <h1>Users</h1>
        <Grid container>
          <Button
            size="small"
            variant="flat"
            color="primary"
            variant="outlined"
            style={{ margin: "2px" }}
            onClick={() => {
              this.updateGenres();
            }}
          >
            Get genres
          </Button>
          <Grid item />
          <Grid item style={{ padding: 20 }} />
        </Grid>

        <ReactTable
          data={this.state.users}
          columns={columns}
          filterable={true}
          defaultFilterMethod={this.customFilter}
          pageSize={5}
          showPaginationTop
          showPaginationBottom
          noDataText={"Plase Wait..."}
        />
        <Snackbar
          open={this.state.open}
          onClose={this.handleClose}
          autoHideDuration={1500}
          message={this.state.message}
        />
      </div>
    );
  }
}

export default Users;
