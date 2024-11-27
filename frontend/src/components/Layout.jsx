import { useState } from "react";
import logo from '../assets/logo.png'
import '../css/Layout.css'
import CookieModal from "./CookieModal";
import { MDBIcon } from "mdb-react-ui-kit";
import { useRole } from "./RoleProvider";

export function Layout({ children }) {
    const [openBasic, setOpenBasic] = useState(false);
    const { isLoggedIn, isShopAssistant } = useRole();

    const isActive = (path) => location.pathname === path ? "active" : "";

    return (
        <>
            <nav style={{ backgroundColor: "#D9D9D9" }} className="navbar navbar-expand-lg navbar-dark">
                <div className="container-fluid">

                    <svg width="102" height="37" viewBox="0 0 102 37" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <rect width="101.361" height="36.2662" fill="#D9D9D9" />
                        <path d="M23.4386 30.2294C23.1186 30.2294 22.8386 30.1614 22.5986 30.0254C22.3666 29.8974 22.2506 29.6694 22.2506 29.3414C22.2506 29.1334 22.3026 28.9014 22.4066 28.6454C22.5506 28.8054 22.6826 28.9174 22.8026 28.9814C22.9306 29.0454 23.0666 29.0774 23.2106 29.0774C23.6106 29.0774 23.9066 28.8534 24.0986 28.4054C24.2986 27.9574 24.3986 27.4614 24.3986 26.9174C24.3986 25.9014 24.0186 25.3934 23.2586 25.3934C23.0426 25.3934 22.8586 25.4054 22.7066 25.4294L21.7226 30.0254H19.9946L21.6266 22.3934L23.4026 22.1534L22.8386 24.8174H22.9106C23.2146 24.8174 23.4986 24.7214 23.7626 24.5294C24.0266 24.3294 24.2386 24.0814 24.3986 23.7854C24.5586 23.4814 24.6386 23.1774 24.6386 22.8734C24.6386 22.5054 24.5066 22.1974 24.2426 21.9494C23.9866 21.7014 23.5906 21.5774 23.0546 21.5774C22.5666 21.5774 22.0906 21.6894 21.6266 21.9134C21.1626 22.1294 20.7826 22.4574 20.4866 22.8974C20.1986 23.3374 20.0546 23.8694 20.0546 24.4934C20.0546 24.7894 20.0826 24.9974 20.1386 25.1174C20.1946 25.2294 20.2226 25.2934 20.2226 25.3094C19.7986 25.3094 19.4786 25.2254 19.2626 25.0574C19.0466 24.8814 18.9386 24.5974 18.9386 24.2054C18.9386 23.6614 19.1426 23.1454 19.5506 22.6574C19.9586 22.1694 20.4986 21.7774 21.1706 21.4814C21.8426 21.1774 22.5506 21.0254 23.2946 21.0254C23.8546 21.0254 24.3266 21.1134 24.7106 21.2894C25.0946 21.4654 25.3786 21.6974 25.5626 21.9854C25.7546 22.2734 25.8506 22.5894 25.8506 22.9334C25.8506 23.3014 25.7466 23.6574 25.5386 24.0014C25.3306 24.3454 25.0346 24.6254 24.6506 24.8414C25.1306 24.9294 25.4866 25.1534 25.7186 25.5134C25.9506 25.8654 26.0666 26.2854 26.0666 26.7734C26.0666 27.2294 25.9786 27.7254 25.8026 28.2614C25.6346 28.7974 25.3506 29.2614 24.9506 29.6534C24.5586 30.0374 24.0546 30.2294 23.4386 30.2294ZM28.4861 23.3774C28.2221 23.3774 27.9981 23.2854 27.8141 23.1014C27.6301 22.9174 27.5381 22.6934 27.5381 22.4294C27.5381 22.1654 27.6301 21.9414 27.8141 21.7574C27.9981 21.5654 28.2221 21.4694 28.4861 21.4694C28.7501 21.4694 28.9741 21.5654 29.1581 21.7574C29.3501 21.9414 29.4461 22.1654 29.4461 22.4294C29.4461 22.6934 29.3501 22.9174 29.1581 23.1014C28.9741 23.2854 28.7501 23.3774 28.4861 23.3774ZM27.6701 30.0974C27.2861 30.0974 26.9741 29.9774 26.7341 29.7374C26.5021 29.4974 26.3861 29.1374 26.3861 28.6574C26.3861 28.4574 26.4181 28.1974 26.4821 27.8774L27.2981 24.0254H29.0261L28.1621 28.1054C28.1301 28.2254 28.1141 28.3534 28.1141 28.4894C28.1141 28.6494 28.1501 28.7654 28.2221 28.8374C28.3021 28.9014 28.4301 28.9334 28.6061 28.9334C28.8381 28.9334 29.0541 28.8374 29.2541 28.6454C29.4541 28.4454 29.5981 28.1894 29.6861 27.8774H30.1901C29.8941 28.7414 29.5141 29.3294 29.0501 29.6414C28.5861 29.9454 28.1261 30.0974 27.6701 30.0974ZM34.1933 30.0974C33.6973 30.0974 33.3213 29.9814 33.0653 29.7494C32.8093 29.5094 32.6813 29.1694 32.6813 28.7294C32.6813 28.5374 32.7053 28.3294 32.7533 28.1054L32.8493 27.6374C32.8813 27.5014 32.8973 27.3534 32.8973 27.1934C32.8973 26.8094 32.7533 26.6174 32.4653 26.6174C32.3373 26.6174 32.2013 26.6534 32.0573 26.7254C31.9213 26.7974 31.7613 26.9094 31.5773 27.0614L30.9533 30.0254H29.2253L30.9653 21.8654L32.7413 21.6254L31.8413 25.8494L34.1573 24.0254H35.3453L32.9453 25.6934C33.0653 25.6614 33.1973 25.6454 33.3413 25.6454C33.7733 25.6454 34.1013 25.7774 34.3253 26.0414C34.5493 26.3054 34.6613 26.6414 34.6613 27.0494C34.6613 27.2174 34.6453 27.3694 34.6133 27.5054L34.4813 28.1054C34.4413 28.2494 34.4213 28.3774 34.4213 28.4894C34.4213 28.7854 34.5773 28.9334 34.8893 28.9334C35.1293 28.9334 35.3213 28.8494 35.4653 28.6814C35.6093 28.5054 35.7533 28.2374 35.8973 27.8774H36.4013C35.9053 29.3574 35.1693 30.0974 34.1933 30.0974ZM37.5842 30.0974C37.0002 30.0974 36.5442 29.9454 36.2162 29.6414C35.8882 29.3294 35.7242 28.8454 35.7242 28.1894C35.7242 27.6374 35.8322 27.0294 36.0482 26.3654C36.2642 25.7014 36.6162 25.1294 37.1042 24.6494C37.5922 24.1614 38.2122 23.9174 38.9642 23.9174C39.8442 23.9174 40.2842 24.3014 40.2842 25.0694C40.2842 25.5174 40.1562 25.9294 39.9002 26.3054C39.6442 26.6814 39.3042 26.9854 38.8802 27.2174C38.4562 27.4414 38.0042 27.5694 37.5242 27.6014C37.5082 27.7774 37.5002 27.8974 37.5002 27.9614C37.5002 28.6254 37.7482 28.9574 38.2442 28.9574C38.4682 28.9574 38.7082 28.8974 38.9642 28.7774C39.2202 28.6574 39.4522 28.5054 39.6602 28.3214C39.4442 29.5054 38.7522 30.0974 37.5842 30.0974ZM37.6082 27.0254C37.9122 27.0174 38.1962 26.9174 38.4602 26.7254C38.7322 26.5334 38.9482 26.2894 39.1082 25.9934C39.2762 25.6894 39.3602 25.3734 39.3602 25.0454C39.3602 24.7174 39.2602 24.5534 39.0602 24.5534C38.7802 24.5534 38.5002 24.8094 38.2202 25.3214C37.9482 25.8254 37.7442 26.3934 37.6082 27.0254ZM43.0468 22.3934L44.8228 22.1534L43.9708 26.1614C44.3548 26.1294 44.7068 25.9614 45.0268 25.6574C45.3548 25.3534 45.6108 24.9774 45.7948 24.5294C45.9788 24.0814 46.0708 23.6414 46.0708 23.2094C46.0708 22.7294 45.9468 22.3374 45.6988 22.0334C45.4508 21.7294 45.0788 21.5774 44.5828 21.5774C43.6068 21.5774 42.8428 21.8334 42.2908 22.3454C41.7468 22.8574 41.4748 23.5734 41.4748 24.4934C41.4748 24.7894 41.5028 24.9974 41.5588 25.1174C41.6148 25.2294 41.6428 25.2934 41.6428 25.3094C41.2108 25.3094 40.8868 25.2214 40.6708 25.0454C40.4628 24.8614 40.3588 24.5614 40.3588 24.1454C40.3588 23.6334 40.5668 23.1374 40.9828 22.6574C41.4068 22.1694 41.9468 21.7774 42.6028 21.4814C43.2588 21.1774 43.9148 21.0254 44.5708 21.0254C45.2028 21.0254 45.7308 21.1334 46.1548 21.3494C46.5788 21.5654 46.8908 21.8534 47.0908 22.2134C47.2988 22.5654 47.4028 22.9574 47.4028 23.3894C47.4028 23.9094 47.2588 24.4174 46.9708 24.9134C46.6908 25.4094 46.2868 25.8174 45.7588 26.1374C45.2308 26.4494 44.6228 26.6054 43.9348 26.6054H43.8748L43.1428 30.0254H41.4148L43.0468 22.3934ZM48.6057 30.0974C48.1497 30.0974 47.7697 29.9414 47.4657 29.6294C47.1617 29.3174 47.0097 28.8294 47.0097 28.1654C47.0097 27.5734 47.1257 26.9534 47.3577 26.3054C47.5977 25.6494 47.9497 25.0974 48.4137 24.6494C48.8857 24.1934 49.4457 23.9654 50.0937 23.9654C50.4217 23.9654 50.6657 24.0214 50.8257 24.1334C50.9857 24.2454 51.0657 24.3934 51.0657 24.5774V24.6614L51.1977 24.0254H52.9257L52.0617 28.1054C52.0297 28.2254 52.0137 28.3534 52.0137 28.4894C52.0137 28.8334 52.1777 29.0054 52.5057 29.0054C52.7297 29.0054 52.9217 28.9014 53.0817 28.6934C53.2497 28.4854 53.3817 28.2134 53.4777 27.8774H53.9817C53.6857 28.7414 53.3177 29.3294 52.8777 29.6414C52.4457 29.9454 52.0097 30.0974 51.5697 30.0974C51.2337 30.0974 50.9617 30.0054 50.7537 29.8214C50.5537 29.6294 50.4337 29.3534 50.3937 28.9934C50.1617 29.3214 49.9017 29.5894 49.6137 29.7974C49.3337 29.9974 48.9977 30.0974 48.6057 30.0974ZM49.3857 28.9334C49.5857 28.9334 49.7817 28.8414 49.9737 28.6574C50.1737 28.4654 50.3097 28.2054 50.3817 27.8774L50.9577 25.1654C50.9577 25.0614 50.9177 24.9614 50.8377 24.8654C50.7577 24.7614 50.6337 24.7094 50.4657 24.7094C50.1457 24.7094 49.8577 24.8974 49.6017 25.2734C49.3457 25.6414 49.1457 26.0894 49.0017 26.6174C48.8577 27.1374 48.7857 27.5974 48.7857 27.9974C48.7857 28.3974 48.8417 28.6534 48.9537 28.7654C49.0737 28.8774 49.2177 28.9334 49.3857 28.9334ZM54.2981 24.0254H56.0261L55.8701 24.7694C56.1421 24.5294 56.3861 24.3454 56.6021 24.2174C56.8261 24.0894 57.0661 24.0254 57.3221 24.0254C57.5781 24.0254 57.7781 24.1134 57.9221 24.2894C58.0741 24.4654 58.1501 24.6774 58.1501 24.9254C58.1501 25.1574 58.0741 25.3614 57.9221 25.5374C57.7701 25.7134 57.5581 25.8014 57.2861 25.8014C57.1101 25.8014 56.9901 25.7614 56.9261 25.6814C56.8701 25.5934 56.8261 25.4694 56.7941 25.3094C56.7701 25.2054 56.7461 25.1294 56.7221 25.0814C56.6981 25.0334 56.6541 25.0094 56.5901 25.0094C56.4221 25.0094 56.2781 25.0454 56.1581 25.1174C56.0461 25.1814 55.8981 25.2974 55.7141 25.4654L54.7541 30.0254H53.0261L54.2981 24.0254ZM59.4338 30.0974C58.9778 30.0974 58.5978 29.9414 58.2938 29.6294C57.9898 29.3174 57.8378 28.8294 57.8378 28.1654C57.8378 27.5734 57.9538 26.9534 58.1858 26.3054C58.4258 25.6494 58.7778 25.0974 59.2418 24.6494C59.7138 24.1934 60.2738 23.9654 60.9218 23.9654C61.2498 23.9654 61.4938 24.0214 61.6538 24.1334C61.8138 24.2454 61.8938 24.3934 61.8938 24.5774V24.6614L62.0258 24.0254H63.7538L62.8898 28.1054C62.8578 28.2254 62.8418 28.3534 62.8418 28.4894C62.8418 28.8334 63.0058 29.0054 63.3338 29.0054C63.5578 29.0054 63.7498 28.9014 63.9098 28.6934C64.0778 28.4854 64.2098 28.2134 64.3058 27.8774H64.8098C64.5138 28.7414 64.1458 29.3294 63.7058 29.6414C63.2738 29.9454 62.8378 30.0974 62.3978 30.0974C62.0618 30.0974 61.7898 30.0054 61.5818 29.8214C61.3818 29.6294 61.2618 29.3534 61.2218 28.9934C60.9898 29.3214 60.7298 29.5894 60.4418 29.7974C60.1618 29.9974 59.8258 30.0974 59.4338 30.0974ZM60.2138 28.9334C60.4138 28.9334 60.6098 28.8414 60.8018 28.6574C61.0018 28.4654 61.1378 28.2054 61.2098 27.8774L61.7858 25.1654C61.7858 25.0614 61.7458 24.9614 61.6658 24.8654C61.5858 24.7614 61.4618 24.7094 61.2938 24.7094C60.9738 24.7094 60.6858 24.8974 60.4298 25.2734C60.1738 25.6414 59.9738 26.0894 59.8298 26.6174C59.6858 27.1374 59.6138 27.5974 59.6138 27.9974C59.6138 28.3974 59.6698 28.6534 59.7818 28.7654C59.9018 28.8774 60.0458 28.9334 60.2138 28.9334ZM65.7502 30.0974C65.2942 30.0974 64.9142 29.9414 64.6102 29.6294C64.3062 29.3174 64.1542 28.8294 64.1542 28.1654C64.1542 27.5734 64.2702 26.9534 64.5022 26.3054C64.7422 25.6494 65.0942 25.0974 65.5582 24.6494C66.0302 24.1934 66.5902 23.9654 67.2382 23.9654C67.5662 23.9654 67.8102 24.0214 67.9702 24.1334C68.1302 24.2454 68.2102 24.3934 68.2102 24.5774V24.6254L68.7982 21.8654L70.5742 21.6254L69.1942 28.1054C69.1622 28.2254 69.1462 28.3534 69.1462 28.4894C69.1462 28.6494 69.1822 28.7654 69.2542 28.8374C69.3342 28.9014 69.4622 28.9334 69.6382 28.9334C69.8622 28.9334 70.0542 28.8374 70.2142 28.6454C70.3822 28.4534 70.5142 28.1974 70.6102 27.8774H71.1142C70.6262 29.3094 69.8222 30.0254 68.7022 30.0254C68.3742 30.0254 68.1062 29.9374 67.8982 29.7614C67.6982 29.5854 67.5742 29.3294 67.5262 28.9934C67.3182 29.3134 67.0662 29.5774 66.7702 29.7854C66.4822 29.9934 66.1422 30.0974 65.7502 30.0974ZM66.5302 28.9334C66.7222 28.9334 66.9142 28.8454 67.1062 28.6694C67.3062 28.4934 67.4422 28.2494 67.5142 27.9374V27.8774L68.1022 25.1294C68.0302 24.8494 67.8662 24.7094 67.6102 24.7094C67.2902 24.7094 67.0022 24.8974 66.7462 25.2734C66.4902 25.6414 66.2902 26.0894 66.1462 26.6174C66.0022 27.1374 65.9302 27.5974 65.9302 27.9974C65.9302 28.3974 65.9862 28.6534 66.0982 28.7654C66.2182 28.8774 66.3622 28.9334 66.5302 28.9334ZM72.6189 23.3774C72.3549 23.3774 72.1309 23.2854 71.9469 23.1014C71.7629 22.9174 71.6709 22.6934 71.6709 22.4294C71.6709 22.1654 71.7629 21.9414 71.9469 21.7574C72.1309 21.5654 72.3549 21.4694 72.6189 21.4694C72.8829 21.4694 73.1069 21.5654 73.2909 21.7574C73.4829 21.9414 73.5789 22.1654 73.5789 22.4294C73.5789 22.6934 73.4829 22.9174 73.2909 23.1014C73.1069 23.2854 72.8829 23.3774 72.6189 23.3774ZM71.8029 30.0974C71.4189 30.0974 71.1069 29.9774 70.8669 29.7374C70.6349 29.4974 70.5189 29.1374 70.5189 28.6574C70.5189 28.4574 70.5509 28.1974 70.6149 27.8774L71.4309 24.0254H73.1589L72.2949 28.1054C72.2629 28.2254 72.2469 28.3534 72.2469 28.4894C72.2469 28.6494 72.2829 28.7654 72.3549 28.8374C72.4349 28.9014 72.5629 28.9334 72.7389 28.9334C72.9709 28.9334 73.1869 28.8374 73.3869 28.6454C73.5869 28.4454 73.7309 28.1894 73.8189 27.8774H74.3229C74.0269 28.7414 73.6469 29.3294 73.1829 29.6414C72.7189 29.9454 72.2589 30.0974 71.8029 30.0974ZM75.1821 30.0974C74.7981 30.0974 74.4741 30.0254 74.2101 29.8814C73.9461 29.7294 73.7501 29.5374 73.6221 29.3054C73.4941 29.0654 73.4301 28.8174 73.4301 28.5614C73.4301 28.2974 73.4941 28.0694 73.6221 27.8774C73.7421 27.6774 73.8901 27.5334 74.0661 27.4454C74.3781 26.8854 74.6501 26.3214 74.8821 25.7534C75.1141 25.1774 75.3341 24.5574 75.5421 23.8934L77.3181 23.6534C77.3581 24.6774 77.4261 25.7854 77.5221 26.9774C77.5621 27.4574 77.5821 27.8054 77.5821 28.0214C77.5821 28.2054 77.5661 28.3574 77.5341 28.4774C77.9101 28.2614 78.1981 28.0614 78.3981 27.8774H78.9021C78.3901 28.4694 77.7781 28.9694 77.0661 29.3774C76.8341 29.6254 76.5461 29.8094 76.2021 29.9294C75.8661 30.0414 75.5261 30.0974 75.1821 30.0974ZM74.7981 29.1494C75.0941 29.1494 75.3421 29.0614 75.5421 28.8854C75.7421 28.7094 75.8421 28.4254 75.8421 28.0334C75.8421 27.7934 75.8181 27.4574 75.7701 27.0254C75.6901 26.1134 75.6381 25.5014 75.6141 25.1894C75.4221 25.8214 75.0981 26.5774 74.6421 27.4574C74.8261 27.5534 74.9181 27.6934 74.9181 27.8774C74.9181 28.0294 74.8661 28.1654 74.7621 28.2854C74.6661 28.4054 74.5421 28.4654 74.3901 28.4654C74.2221 28.4654 74.1141 28.4134 74.0661 28.3094C74.0661 28.5974 74.1221 28.8094 74.2341 28.9454C74.3541 29.0814 74.5421 29.1494 74.7981 29.1494ZM80.0881 30.0974C79.5041 30.0974 79.0481 29.9454 78.7201 29.6414C78.3921 29.3294 78.2281 28.8454 78.2281 28.1894C78.2281 27.6374 78.3361 27.0294 78.5521 26.3654C78.7681 25.7014 79.1201 25.1294 79.6081 24.6494C80.0961 24.1614 80.7161 23.9174 81.4681 23.9174C82.3481 23.9174 82.7881 24.3014 82.7881 25.0694C82.7881 25.5174 82.6601 25.9294 82.4041 26.3054C82.1481 26.6814 81.8081 26.9854 81.3841 27.2174C80.9601 27.4414 80.5081 27.5694 80.0281 27.6014C80.0121 27.7774 80.0041 27.8974 80.0041 27.9614C80.0041 28.6254 80.2521 28.9574 80.7481 28.9574C80.9721 28.9574 81.2121 28.8974 81.4681 28.7774C81.7241 28.6574 81.9561 28.5054 82.1641 28.3214C81.9481 29.5054 81.2561 30.0974 80.0881 30.0974ZM80.1121 27.0254C80.4161 27.0174 80.7001 26.9174 80.9641 26.7254C81.2361 26.5334 81.4521 26.2894 81.6121 25.9934C81.7801 25.6894 81.8641 25.3734 81.8641 25.0454C81.8641 24.7174 81.7641 24.5534 81.5641 24.5534C81.2841 24.5534 81.0041 24.8094 80.7241 25.3214C80.4521 25.8254 80.2481 26.3934 80.1121 27.0254Z" fill="black" />
                        <path d="M51.5375 5.5415C51.7953 5.5415 52.0531 5.70557 52.1937 5.93994L54.4906 10.1821C54.7719 10.1118 55.0766 10.0415 55.4047 10.0415C57.0453 10.0415 58.4047 11.4009 58.4047 13.0415C58.4047 14.7056 57.0453 16.0415 55.4047 16.0415C53.7406 16.0415 52.4047 14.7056 52.4047 13.0415C52.4047 12.104 52.8266 11.2837 53.5062 10.7212L52.9906 9.78369L50.9984 13.1587C50.9047 13.3228 50.7172 13.4165 50.5297 13.4165H49.3812C49.1937 14.9165 47.9281 16.0415 46.4047 16.0415C44.7406 16.0415 43.4047 14.7056 43.4047 13.0415C43.4047 11.4009 44.7406 10.0415 46.4047 10.0415C46.6391 10.0415 46.8969 10.0884 47.1312 10.1353L47.7406 8.89307L47.225 8.1665H46.2172C45.8891 8.1665 45.6547 7.93213 45.6547 7.604C45.6547 7.29932 45.8891 7.0415 46.2172 7.0415H47.5297C47.6937 7.0415 47.8578 7.13525 47.975 7.27588L48.6078 8.1665H52.1234L51.3031 6.6665H50.6937C50.3891 6.6665 50.1312 6.43213 50.1312 6.104C50.1312 5.79932 50.3891 5.5415 50.6937 5.5415H51.5375ZM53.7172 13.0415C53.7172 13.979 54.4672 14.729 55.4047 14.729C56.3187 14.729 57.0922 13.979 57.0922 13.0415C57.0922 12.1274 56.3187 11.354 55.4047 11.354C55.3109 11.354 55.2172 11.3774 55.1234 11.3774L55.8969 12.7837C56.0375 13.0649 55.9437 13.3931 55.6625 13.5571C55.3812 13.6978 55.0531 13.604 54.8891 13.3228L54.1391 11.9165C53.8812 12.2212 53.7172 12.6196 53.7172 13.0415ZM45.9359 12.5024L46.5219 11.3774C46.475 11.3774 46.4281 11.354 46.3812 11.354C45.4672 11.354 44.6937 12.1274 44.6937 13.0415C44.6937 13.979 45.4672 14.729 46.3812 14.729C47.2016 14.729 47.8578 14.1899 48.0453 13.4165H46.5219C46.0297 13.4165 45.725 12.9243 45.9359 12.5024ZM50.2016 12.2915L48.4906 9.92432L47.2953 12.2915H50.2016ZM49.4281 9.2915L50.8344 11.2368L51.9594 9.2915H49.4281Z" fill="black" />
                    </svg>


                    <button className="navbar-toggler" type="button" data-mdb-toggle="collapse" data-mdb-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <i className="fas fa-bars navbar-elem"></i>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <a className="nav-link" aria-label="Home page" href="/"><MDBIcon fas icon="home" className={`${isActive('/') ? "active-elem" : "navbar-elem"}`} /></a>
                            </li>
                            <li className="nav-item">
                                <a className={`nav-link ${isActive('/bike-shop') ? "active-elem" : "navbar-elem"}`} aria-label="Bike shop" href="/bike-shop">Bikes</a>
                            </li>

                            <li className="nav-item">
                                <a className={`nav-link ${isActive('/part-shop') ? "active-elem" : "navbar-elem"}`} aria-label="Part shop" href="/part-shop">Parts</a>
                            </li>

                        </ul>

                        <ul className="navbar-nav d-flex flex-row me-1">
                            {isLoggedIn ?
                                <li className="nav-item me-3 me-lg-0">
                                    <a className="nav-link" aria-label="Order list" href="/order-list"><MDBIcon fas icon="envelope" className={`${isActive('/order-list') ? "active-elem" : "navbar-elem"}`} /></a>
                                </li>
                                : null}
                            {!isShopAssistant ?
                                <li className="nav-item me-3 me-lg-0">
                                    <a className="nav-link" aria-label="Shopping cart" href="/cart"><MDBIcon fas icon="shopping-cart" className={`${isActive('/cart') ? "active-elem" : "navbar-elem"}`} /></a>
                                </li>
                                : null}
                            <li className="nav-item me-3 me-lg-0">
                                <a className="nav-link" aria-label="Account" href="/account"><MDBIcon fas icon="user-alt" className={`${isActive('/account') || isActive('/sign-in') || isActive('/register') ? "active-elem" : "navbar-elem"}`} /></a>
                            </li>
                        </ul>

                    </div>
                </div>
            </nav>

            <main>
                {children}
            </main>
            <CookieModal />
        </>
    );
}