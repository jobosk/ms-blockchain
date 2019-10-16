pragma solidity >=0.4.22 <0.6.0;

import "./SafeMath.sol";

contract Patient {

    using SafeMath for uint256;

    address private owner;

    constructor () public {
        owner = msg.sender;
    }

    function getPatientId() public view returns (uint256) {
        return 1;
    }
}