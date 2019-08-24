DELETE FROM user;

INSERT INTO user (id, name, age, email, wallet, other_info) VALUES
(1, 'Jone', 18, 'test1@baomidou.com', '{
    "wallet": {
        "name": "支付宝钱包",
        "currency": [{
            "type": "USD",
            "amount": 999.19
        },{
            "type": "RMB",
            "amount": 1000.19
        }]
    }
}', '{
    "otherInfo": {
        "sex": "男",
        "city": "南昌"
    }
}'),
(2, 'Jack', 20, 'test2@baomidou.com', '{
    "wallet": {
        "name": "微信钱包",
        "currency": [{
            "type": "USD",
            "amount": 888.18
        },{
            "type": "RMB",
            "amount": 1000.18
        }]
    }
}', '{
    "otherInfo": {
        "sex": "男",
        "city": "青岛"
    }
}');