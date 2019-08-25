DELETE FROM user;

INSERT INTO user (id, name, age, email, wallet, other_info) VALUES
(1, 'Jone', 18, 'test1@baomidou.com', '{
    "name": "支付宝钱包",
    "currencyList": [{
        "type": "USD",
        "amount": 999.19
    },{
        "type": "RMB",
        "amount": 1000.19
    }]
}', '{
        "sex": "男",
        "city": "南昌"
}'),
(2, 'Jack', 20, 'test2@baomidou.com', '{
    "name": "微信钱包",
    "currencyList": [{
        "type": "USD",
        "amount": 888.18
    },{
        "type": "RMB",
        "amount": 1000.18
    }]
}', '{
        "sex": "男",
        "city": "青岛"
}');