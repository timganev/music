import React from "react";
// import { Link } from "react-router-dom";
// import SignedInLinks from "./SignedInLinks";
// import SignedOutLinks from "./SignedOutLinks";
import { connect } from "react-redux";

const Navbar = props => {
  const { auth, profile } = props;
  // console.log(auth);
  // const links = auth.uid ? (
  //   <SignedInLinks profile={profile} />
  // ) : (
  //   <SignedOutLinks />
  // );

  return (
    <div class="navbar-fixed">
      <nav className="nav-wrapper grey darken-3" fixed="top" scrolling>
        <div className="container">
          <h5>Wellcome guest</h5>
          {/* <Link to="/" className="brand-logo">
            MarioPlan
          </Link>
          {links} */}
        </div>
      </nav>
    </div>
  );
};

const mapStateToProps = state => {
  // console.log(state);
  return {
    auth: state.firebase.auth,
    profile: state.firebase.profile
  };
};

// export default connect(mapStateToProps)(Navbar);
export default Navbar;
